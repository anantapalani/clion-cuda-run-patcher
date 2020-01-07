import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.Function;
import com.jetbrains.cidr.cpp.execution.testing.CMakeTestLauncher;
import com.jetbrains.cidr.execution.testing.CidrLauncher;
import com.jetbrains.cidr.execution.testing.CidrRerunFailedTestsAction;
import com.jetbrains.cidr.execution.testing.CidrTestRunConfiguration;
import com.jetbrains.cidr.execution.testing.CidrTestRunConfigurationData;
import com.jetbrains.cidr.execution.testing.CidrTestScope;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

// analogous to com.jetbrains.cidr.cpp.execution.testing.CMakeTestRunConfiguration

public class CMakeRunPatcherTestRunConfiguration extends CMakeRunPatcherAppRunConfiguration implements CidrTestRunConfiguration {
    private CidrTestRunConfigurationData myTestData;

    protected CMakeRunPatcherTestRunConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory, @NotNull String name, @NotNull Function<? super CidrTestRunConfiguration, ? extends CidrTestRunConfigurationData> testDataFactory) {
        super(project, factory, name);
        this.myTestData = testDataFactory.fun(this);
    }
    @NotNull
    public CidrTestRunConfigurationData getTestData() {
        return this.myTestData;
    }

    public CommandLineState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment env) {
        return this.createState(env, executor, null);
    }

    @NotNull
    public CidrRerunFailedTestsAction.CidrReturnTestProfile createTestRunProfile(CidrRerunFailedTestsAction action, CidrTestScope scope) {
        return new CidrRerunFailedTestsAction.CidrReturnTestProfile(action, this, scope);
    }

    public String suggestedName() {
        return this.myTestData.suggestedName(this.suggestNameForTarget());
    }

    public void checkConfiguration() throws RuntimeConfigurationException {
        super.checkConfiguration();
        this.myTestData.checkData();
    }

    public void readExternal(@NotNull Element element) throws InvalidDataException {
        super.readExternal(element);
        this.myTestData.readExternal(element);
    }

    public void writeExternal(@NotNull Element element) throws WriteExternalException {
        super.writeExternal(element);
        this.myTestData.writeExternal(element);
    }

    public RunConfiguration clone() {
        CMakeRunPatcherTestRunConfiguration clonedConfiguration = (CMakeRunPatcherTestRunConfiguration)super.clone();
        clonedConfiguration.myTestData = this.myTestData.cloneForConfiguration(clonedConfiguration);
        return clonedConfiguration;
    }

    @NotNull
    public CidrLauncher createLauncher(@NotNull ExecutionEnvironment environment) {
        return new CMakeTestLauncher(environment, this);
    }
}
