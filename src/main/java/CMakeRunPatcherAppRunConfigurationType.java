import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NotNullLazyValue;
import com.jetbrains.cidr.CidrBundle;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfigurationSettingsEditor;
import com.jetbrains.cidr.cpp.execution.CMakeRunConfigurationType;
import org.jetbrains.annotations.NotNull;

// replaces com.jetbrains.cidr.cpp.execution.CMakeAppRunConfigurationType

public class CMakeRunPatcherAppRunConfigurationType extends CMakeRunConfigurationType {
    public CMakeRunPatcherAppRunConfigurationType() {
        super("CMakeRunConfiguration", "Application", CidrBundle.message("run.configuration.name", new Object[0]), CidrBundle.message("run.configuration.description", new Object[0]), NotNullLazyValue.createValue(() -> AllIcons.RunConfigurations.Application));
    }

    @NotNull
    protected CMakeAppRunConfiguration createRunConfiguration(@NotNull Project project, @NotNull ConfigurationFactory configurationFactory) {
        return new CMakeRunPatcherAppRunConfiguration(project, configurationFactory, "");
    }

    public SettingsEditor<? extends CMakeAppRunConfiguration> createEditor(@NotNull Project project) {
        return new CMakeAppRunConfigurationSettingsEditor(project, getHelper(project));
    }

    public String getHelpTopic() {
        return "reference.dialogs.rundebug.CMakeRunConfiguration";
    }

    @NotNull
    public static CMakeRunPatcherAppRunConfigurationType getInstance() {
        return (CMakeRunPatcherAppRunConfigurationType)ConfigurationTypeUtil.findConfigurationType(CMakeRunPatcherAppRunConfigurationType.class);
    }
}
