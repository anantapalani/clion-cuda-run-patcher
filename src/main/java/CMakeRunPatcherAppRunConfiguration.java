import com.intellij.execution.ExecutionTarget;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.jetbrains.cidr.cpp.cmake.model.*;
import com.jetbrains.cidr.cpp.cmake.workspace.CMakeWorkspace;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration;
import com.jetbrains.cidr.cpp.execution.CMakeBuildProfileExecutionTarget;
import com.jetbrains.cidr.execution.BuildConfigurationProblems;
import com.jetbrains.cidr.lang.OCLanguageKind;
import com.jetbrains.cmake.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.io.LocalFileFinder;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CMakeRunPatcherAppRunConfiguration extends CMakeAppRunConfiguration {
    protected CMakeRunPatcherAppRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @Override
    @Nullable
    public CMakeAppRunConfiguration.BuildAndRunConfigurations getBuildAndRunConfigurations(@NotNull ExecutionTarget target) {
        return this.getBuildAndRunConfigurations(target, null, false);
    }

    @Override
    @Nullable
    public CMakeAppRunConfiguration.BuildAndRunConfigurations getBuildAndRunConfigurations(@NotNull ExecutionTarget target, @Nullable BuildConfigurationProblems configurationProblems, boolean b) {
        return this.getBuildAndRunConfigurations(CMakeBuildProfileExecutionTarget.getProfileName(target), configurationProblems, b);
    }

    @Override
    @Nullable
    public CMakeAppRunConfiguration.BuildAndRunConfigurations getBuildAndRunConfigurations(@NotNull String configurationName) {
        return this.getBuildAndRunConfigurations(configurationName, null, false);
    }

    @Override
    @Nullable
    public CMakeAppRunConfiguration.BuildAndRunConfigurations getBuildAndRunConfigurations(@Nullable String configurationName, @Nullable BuildConfigurationProblems configurationProblems, boolean b) {
        CMakeAppRunConfiguration.BuildAndRunConfigurations config = super.getBuildAndRunConfigurations(configurationName, configurationProblems, b);

        if (config != null) {
            CMakeConfiguration buildConfiguration = patchConfiguration(config.buildConfiguration);
            CMakeConfiguration runConfiguration = patchConfiguration(config.runConfiguration);
            File runExecutable = patchRunExecutable(config.runExecutable, buildConfiguration, runConfiguration);

            return new BuildAndRunConfigurations(buildConfiguration, runConfiguration, runExecutable, config.explicitBuildTargetName);
        }

        return null;
    }

    public static class BuildAndRunConfigurations extends CMakeAppRunConfiguration.BuildAndRunConfigurations {
        public BuildAndRunConfigurations(@NotNull CMakeConfiguration buildConfiguration, @Nullable CMakeConfiguration runConfiguration, @Nullable File runExecutable, @Nullable String explicitBuildTargetName) {
            super(buildConfiguration, runConfiguration, runExecutable, explicitBuildTargetName);
        }

        public BuildAndRunConfigurations(@NotNull CMakeConfiguration buildConfiguration) {
            super(buildConfiguration);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private CMakeConfiguration patchConfiguration(@Nullable final CMakeConfiguration configuration) {
        CMakeConfiguration patchedConfiguration = null;

        if (configuration != null) {
            try {
                Method method = configuration.getClass().getDeclaredMethod("getConfigurationAndTargetGenerationDir");
                method.setAccessible(true);
                File configurationAndTargetGenerationDir = (File) method.invoke(configuration);

                method = configuration.getClass().getDeclaredMethod("getPerLanguageSettingsMap");
                method.setAccessible(true);
                final Map<OCLanguageKind, CMakeConfigurationSettings> perLanguageSettingsMap = (Map<OCLanguageKind, CMakeConfigurationSettings>) method.invoke(configuration);

                method = configuration.getClass().getDeclaredMethod("getSourcesMap");
                method.setAccessible(true);
                final Map<File, CMakeFileSettings> sourcesMap = (Map<File, CMakeFileSettings>) method.invoke(configuration);

                final File productFile = patchExecutable(configuration.getProductFile(), configuration);

                patchedConfiguration = new CMakeConfiguration(
                        configuration.getProfileId(),
                        configuration.getProfileName(),
                        configuration.getBuildType(),
                        configuration.getConfigurationGenerationDir(),
                        configurationAndTargetGenerationDir,
                        perLanguageSettingsMap,
                        configuration.getLinkerFlags(),
                        sourcesMap,
                        productFile,
                        configuration.getBuildWorkingDir(),
                        configuration.getTargetType(),
                        configuration.getGenerator()
                );

                method = patchedConfiguration.getClass().getDeclaredMethod("initTarget", CMakeTarget.class);
                method.setAccessible(true);
                method.invoke(patchedConfiguration, configuration.getTarget());
            } catch (NoSuchMethodException e) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }

        return patchedConfiguration;
    }

    @Nullable
    private File patchRunExecutable(@Nullable final File runExecutable, @Nullable final CMakeConfiguration buildConfiguration, @Nullable final CMakeConfiguration runConfiguration) {
        File patchedRunExecutable = runExecutable;

        // check if we have encountered https://youtrack.jetbrains.com/issue/CPP-10292
        if (runExecutable != null && runExecutable.toString().endsWith(".o")) {
            if (runConfiguration != null) {
                patchedRunExecutable = runConfiguration.getProductFile();
            } else if (buildConfiguration != null) {
                patchedRunExecutable = buildConfiguration.getProductFile();
            }
        }

        return patchedRunExecutable;
    }

    @Nullable
    private File patchExecutable(@Nullable final File executableFile, @Nullable final CMakeConfiguration configuration) {
        File patchedExecutableFile = executableFile;

        if (executableFile != null) {
            String executableFileString = executableFile.toString();
            // check if we have encountered https://youtrack.jetbrains.com/issue/CPP-10292
            if (executableFileString.endsWith(".o")) {
                if (configuration != null) {
                    String outputName = getOutputNameForConfiguration(configuration);

                    if (outputName == null) {
                        outputName = getOutputNameForTarget(configuration.getTarget());
                    }

                    String correctedExePathString = configuration.getBuildWorkingDir().toString() + File.separator + outputName;
                    patchedExecutableFile = new File(correctedExePathString);
                }
            }
        }

        return patchedExecutableFile;
    }

    @Nullable
    private String getOutputNameForConfiguration(@Nullable final CMakeConfiguration configuration) {
        String outputName = null;

        if (configuration != null) {
            List<String> linkerFlags = configuration.getLinkerFlags();

            for (int i=0; i+1<linkerFlags.size(); i++) {
                if (linkerFlags.get(i).equalsIgnoreCase("-o")) {
                    outputName = linkerFlags.get(i+1);
                }
            }
        }

        return outputName;
    }

    @Nullable
    private String getOutputNameForTarget(@Nullable final CMakeTarget target) {
        String outputName = null;

        if (target != null) {
            final String targetName = target.getName();
            final PsiManager psiManager = PsiManager.getInstance(this.getProject());
            final CMakeWorkspace workspace = CMakeWorkspace.getInstance(this.getProject());

            outputName = targetName;

            if (psiManager != null && workspace != null) {
                for (File file : workspace.getCMakeDependencyFiles()) {
                    if (file.toString().endsWith("CMakeLists.txt")) {
                        VirtualFile virtualFile = LocalFileFinder.findFile(file.toString());

                        if (virtualFile != null) {
                            CMakeFile cMakeFile = (CMakeFile)psiManager.findFile(virtualFile);

                            if (cMakeFile != null) {
                                List<CMakeCommand> setTargetPropertyCommands = cMakeFile.getTopLevelCommands("set_target_properties");

                                for (CMakeCommand command : setTargetPropertyCommands) {
                                    List<CMakeArgument> arguments = command.getCMakeCommandArguments().getCMakeArgumentList();

                                    if (arguments.size() >= 4) {
                                        boolean commandAppliesToTarget = false;

                                        for (int i=0; i < arguments.size(); ++i) {
                                            String argValue = arguments.get(i).getValue();

                                            if (argValue.equals(targetName)) {
                                                commandAppliesToTarget = true;
                                            } else if (argValue.equalsIgnoreCase("PROPERTIES")) {
                                                if (commandAppliesToTarget) {
                                                    HashMap<String, String> properties = new HashMap<>();

                                                    for (int j=++i; j+1 < arguments.size(); j+=2) {
                                                        String property = arguments.get(j).getValue().toUpperCase();
                                                        String value = arguments.get(j+1).getValue();
                                                        properties.put(property, value);
                                                    }

                                                    String customOutputName = properties.get("OUTPUT_NAME");
                                                    if (customOutputName != null) {
                                                        outputName = customOutputName;
                                                    }
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return outputName;
    }
}
