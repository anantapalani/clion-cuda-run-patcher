import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NotNullLazyValue;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration;
import com.jetbrains.cidr.cpp.execution.CMakeRunConfigurationType;
import com.jetbrains.cidr.execution.testing.CidrBeforeTestRunTaskProvider;
import com.jetbrains.cidr.execution.testing.boost.CidrBoostBundle;
import com.jetbrains.cidr.execution.testing.boost.CidrBoostTestRunConfigurationData;
import com.jetbrains.cidr.execution.testing.boost.CidrBoostTestRunConfigurationEditor;
import icons.CidrBoostIcons;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

// replaces com.jetbrains.cidr.cpp.execution.testing.boost.CMakeBoostTestRunConfigurationType

public class CMakeRunPatcherBoostTestRunConfigurationType extends CMakeRunConfigurationType implements CidrBeforeTestRunTaskProvider.CidrBeforeRunTaskConverter {
    protected CMakeRunPatcherBoostTestRunConfigurationType() {
        super("CMakeBoostTestRunConfigurationType", "Boost Test", CidrBoostBundle.message("boost.test.configuration.name", new Object[0]), CidrBoostBundle.message("boost.test.configuration.description", new Object[0]), NotNullLazyValue.createValue(() -> CidrBoostIcons.BoostTest));
    }

    @NotNull
    @Contract("_, _ -> new")
    protected CMakeAppRunConfiguration createRunConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory) {
        return new CMakeRunPatcherTestRunConfiguration(project, factory, "", CidrBoostTestRunConfigurationData.FACTORY);
    }

    @NotNull
    @Contract("_ -> new")
    @SuppressWarnings("unchecked")
    public SettingsEditor<? extends CMakeAppRunConfiguration> createEditor(@NotNull Project project) {
        return new CidrBoostTestRunConfigurationEditor(project, CMakeRunConfigurationType.getHelper(project));
    }

    @NotNull
    @Contract(pure = true)
    public String getHelpTopic() {
        return "reference.dialogs.rundebug.CMakeBoostTestRunConfigurationType";
    }

    @NotNull
    public static CMakeRunPatcherBoostTestRunConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(CMakeRunPatcherBoostTestRunConfigurationType.class);
    }
}
