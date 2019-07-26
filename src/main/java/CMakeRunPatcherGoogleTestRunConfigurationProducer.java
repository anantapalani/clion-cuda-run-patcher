import com.intellij.execution.configurations.ConfigurationFactory;
import com.jetbrains.cidr.cpp.cmake.model.CMakeConfiguration;
import com.jetbrains.cidr.cpp.cmake.model.CMakeTarget;
import com.jetbrains.cidr.cpp.execution.CMakeTargetRunConfigurationBinder;
import com.jetbrains.cidr.cpp.execution.testing.CMakeTestRunConfiguration;
import com.jetbrains.cidr.execution.testing.CidrTestScopeElement;
import com.jetbrains.cidr.execution.testing.CidrTestWithScopeElementsRunConfigurationProducer;
import com.jetbrains.cidr.execution.testing.google.CidrGoogleTestFramework;
import org.jetbrains.annotations.NotNull;

// analogous to com.jetbrains.cidr.cpp.execution.testing.google.CMakeGoogleTestRunConfigurationProducer

public class CMakeRunPatcherGoogleTestRunConfigurationProducer extends CidrTestWithScopeElementsRunConfigurationProducer<CMakeConfiguration, CMakeTarget, CMakeTestRunConfiguration, CidrTestScopeElement, CidrGoogleTestFramework> {
    public CMakeRunPatcherGoogleTestRunConfigurationProducer() {
        super(CMakeTargetRunConfigurationBinder.INSTANCE, CidrGoogleTestFramework.class);
    }

    @NotNull
    public ConfigurationFactory getConfigurationFactory() {
        return CMakeRunPatcherGoogleTestRunConfigurationType.getInstance().getFactory();
    }
}
