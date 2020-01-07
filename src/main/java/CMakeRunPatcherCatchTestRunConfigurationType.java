import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NotNullLazyValue;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration;
import com.jetbrains.cidr.cpp.execution.CMakeRunConfigurationType;
import com.jetbrains.cidr.execution.testing.CidrBeforeTestRunTaskProvider;
import com.jetbrains.cidr.execution.testing.tcatch.CidrCatchBundle;
import com.jetbrains.cidr.execution.testing.tcatch.CidrCatchTestRunConfigurationData;
import com.jetbrains.cidr.execution.testing.tcatch.CidrCatchTestRunConfigurationEditor;
import icons.CidrCatchIcons;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

// replaces com.jetbrains.cidr.cpp.execution.testing.tcatch.CMakeCatchTestRunConfigurationType

public class CMakeRunPatcherCatchTestRunConfigurationType extends CMakeRunConfigurationType implements CidrBeforeTestRunTaskProvider.CidrBeforeRunTaskConverter {
    protected CMakeRunPatcherCatchTestRunConfigurationType() {
        super("CMakeCatchTestRunConfigurationType", "Catch Test", CidrCatchBundle.message("catch.test.configuration.name", new Object[0]), CidrCatchBundle.message("catch.test.configuration.description", new Object[0]), NotNullLazyValue.createValue(() -> CidrCatchIcons.CatchTest));
    }

    @NotNull
    @Contract("_, _ -> new")
    protected CMakeAppRunConfiguration createRunConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory) {
        return new CMakeRunPatcherTestRunConfiguration(project, factory, "", CidrCatchTestRunConfigurationData.FACTORY);
    }

    @NotNull
    @Contract("_ -> new")
    @SuppressWarnings("unchecked")
    public SettingsEditor<? extends CMakeAppRunConfiguration> createEditor(@NotNull Project project) {
        return new CidrCatchTestRunConfigurationEditor(project, CMakeRunConfigurationType.getHelper(project));
    }

    @NotNull
    @Contract(pure = true)
    public String getHelpTopic() {
        return "reference.dialogs.rundebug.CMakeCatchTestRunConfigurationType";
    }

    @NotNull
    public static CMakeRunPatcherCatchTestRunConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(CMakeRunPatcherCatchTestRunConfigurationType.class);
    }
}
