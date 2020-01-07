import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NotNullLazyValue;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration;
import com.jetbrains.cidr.cpp.execution.CMakeRunConfigurationType;
import com.jetbrains.cidr.execution.testing.CidrBeforeTestRunTaskProvider;
import com.jetbrains.cidr.execution.testing.google.CidrGoogleBundle;
import com.jetbrains.cidr.execution.testing.google.CidrGoogleTestRunConfigurationData;
import com.jetbrains.cidr.execution.testing.google.CidrGoogleTestRunConfigurationEditor;
import icons.CidrGoogleIcons;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

// replaces com.jetbrains.cidr.cpp.execution.testing.google.CMakeGoogleTestRunConfigurationType

public class CMakeRunPatcherGoogleTestRunConfigurationType extends CMakeRunConfigurationType implements CidrBeforeTestRunTaskProvider.CidrBeforeRunTaskConverter {
    protected CMakeRunPatcherGoogleTestRunConfigurationType() {
        super("CMakeGoogleTestRunConfigurationType", "Google Test", CidrGoogleBundle.message("gtest.test.configuration.name", new Object[0]), CidrGoogleBundle.message("gtest.test.configuration.description", new Object[0]), NotNullLazyValue.createValue(() -> CidrGoogleIcons.GoogleTest));
    }

    @NotNull
    @Contract("_, _ -> new")
    protected CMakeAppRunConfiguration createRunConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory) {
        return new CMakeRunPatcherTestRunConfiguration(project, factory, "", CidrGoogleTestRunConfigurationData.FACTORY);
    }

    @NotNull
    @Contract("_ -> new")
    @SuppressWarnings("unchecked")
    public SettingsEditor<? extends CMakeAppRunConfiguration> createEditor(@NotNull Project project) {
        return new CidrGoogleTestRunConfigurationEditor(project, CMakeRunConfigurationType.getHelper(project));
    }

    @NotNull
    @Contract(pure = true)
    public String getHelpTopic() {
        return "reference.dialogs.rundebug.CMakeGoogleTestRunConfigurationType";
    }

    @NotNull
    public static CMakeRunPatcherGoogleTestRunConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(CMakeRunPatcherGoogleTestRunConfigurationType.class);
    }
}
