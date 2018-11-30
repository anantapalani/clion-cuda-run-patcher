import com.jetbrains.cidr.cpp.cmake.model.CMakeConfiguration;
import com.jetbrains.cidr.cpp.cmake.model.CMakeTarget;
import com.jetbrains.cidr.cpp.execution.CMakeTargetRunConfigurationBinder;
import com.jetbrains.cidr.cpp.execution.testing.CMakeTestRunConfiguration;
import com.jetbrains.cidr.execution.testing.CidrTestScopeElement;
import com.jetbrains.cidr.execution.testing.CidrTestWithScopeElementsRunConfigurationProducer;
import com.jetbrains.cidr.execution.testing.google.CidrGoogleTestFramework;

// analogous to com.jetbrains.cidr.cpp.execution.testing.google.CMakeGoogleTestRunConfigurationProducer

public class CMakeRunPatcherGoogleTestRunConfigurationProducer extends CidrTestWithScopeElementsRunConfigurationProducer<CMakeConfiguration, CMakeTarget, CMakeTestRunConfiguration, CidrTestScopeElement, CidrGoogleTestFramework> {
    public CMakeRunPatcherGoogleTestRunConfigurationProducer() {
        super(CMakeRunPatcherGoogleTestRunConfigurationType.getInstance(), CMakeTargetRunConfigurationBinder.INSTANCE, CidrGoogleTestFramework.class);
    }
}
