import com.jetbrains.cidr.cpp.cmake.model.CMakeConfiguration;
import com.jetbrains.cidr.cpp.cmake.model.CMakeTarget;
import com.jetbrains.cidr.cpp.execution.CMakeTargetRunConfigurationBinder;
import com.jetbrains.cidr.cpp.execution.testing.CMakeTestRunConfiguration;
import com.jetbrains.cidr.execution.testing.CidrTestScopeElement;
import com.jetbrains.cidr.execution.testing.CidrTestWithScopeElementsRunConfigurationProducer;
import com.jetbrains.cidr.execution.testing.boost.CidrBoostTestFramework;

// analogous to com.jetbrains.cidr.cpp.execution.testing.boost.CMakeBoostTestRunConfigurationProducer

public class CMakeRunPatcherBoostTestRunConfigurationProducer extends CidrTestWithScopeElementsRunConfigurationProducer<CMakeConfiguration, CMakeTarget, CMakeTestRunConfiguration, CidrTestScopeElement, CidrBoostTestFramework> {
    public CMakeRunPatcherBoostTestRunConfigurationProducer() {
        super(CMakeRunPatcherBoostTestRunConfigurationType.getInstance(), CMakeTargetRunConfigurationBinder.INSTANCE, CidrBoostTestFramework.class);
    }
}
