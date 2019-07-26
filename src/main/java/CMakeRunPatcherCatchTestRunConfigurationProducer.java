import com.intellij.execution.configurations.ConfigurationFactory;
import com.jetbrains.cidr.cpp.cmake.model.CMakeConfiguration;
import com.jetbrains.cidr.cpp.cmake.model.CMakeTarget;
import com.jetbrains.cidr.cpp.execution.CMakeTargetRunConfigurationBinder;
import com.jetbrains.cidr.cpp.execution.testing.CMakeTestRunConfiguration;
import com.jetbrains.cidr.execution.testing.CidrTestScopeElement;
import com.jetbrains.cidr.execution.testing.CidrTestWithScopeElementsRunConfigurationProducer;
import com.jetbrains.cidr.execution.testing.tcatch.CidrCatchTestFramework;
import org.jetbrains.annotations.NotNull;

// analogous to com.jetbrains.cidr.cpp.execution.testing.tcatch.CMakeCatchTestRunConfigurationProducer

public class CMakeRunPatcherCatchTestRunConfigurationProducer extends CidrTestWithScopeElementsRunConfigurationProducer<CMakeConfiguration, CMakeTarget, CMakeTestRunConfiguration, CidrTestScopeElement, CidrCatchTestFramework> {
    public CMakeRunPatcherCatchTestRunConfigurationProducer() {
        super(CMakeTargetRunConfigurationBinder.INSTANCE, CidrCatchTestFramework.class);
    }

    @NotNull
    public ConfigurationFactory getConfigurationFactory() {
        return CMakeRunPatcherCatchTestRunConfigurationType.getInstance().getFactory();
    }
}
