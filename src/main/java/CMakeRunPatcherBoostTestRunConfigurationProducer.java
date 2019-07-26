import com.intellij.execution.configurations.ConfigurationFactory;
import com.jetbrains.cidr.cpp.cmake.model.CMakeConfiguration;
import com.jetbrains.cidr.cpp.cmake.model.CMakeTarget;
import com.jetbrains.cidr.cpp.execution.CMakeTargetRunConfigurationBinder;
import com.jetbrains.cidr.cpp.execution.testing.CMakeTestRunConfiguration;
import com.jetbrains.cidr.execution.testing.CidrTestScopeElement;
import com.jetbrains.cidr.execution.testing.CidrTestWithScopeElementsRunConfigurationProducer;
import com.jetbrains.cidr.execution.testing.boost.CidrBoostTestFramework;
import org.jetbrains.annotations.NotNull;

// analogous to com.jetbrains.cidr.cpp.execution.testing.boost.CMakeBoostTestRunConfigurationProducer

public class CMakeRunPatcherBoostTestRunConfigurationProducer extends CidrTestWithScopeElementsRunConfigurationProducer<CMakeConfiguration, CMakeTarget, CMakeTestRunConfiguration, CidrTestScopeElement, CidrBoostTestFramework> {
    public CMakeRunPatcherBoostTestRunConfigurationProducer() {
        super(CMakeTargetRunConfigurationBinder.INSTANCE, CidrBoostTestFramework.class);
    }

    @NotNull
    public ConfigurationFactory getConfigurationFactory() {
        return CMakeRunPatcherBoostTestRunConfigurationType.getInstance().getFactory();
    }
}
