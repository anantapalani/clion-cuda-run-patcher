import com.jetbrains.cidr.cpp.cmake.model.CMakeConfiguration;
import com.jetbrains.cidr.cpp.cmake.model.CMakeTarget;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration;
import com.jetbrains.cidr.cpp.execution.CMakeTargetRunConfigurationBinder;
import com.jetbrains.cidr.execution.CidrTargetRunConfigurationProducer;

public class CMakeRunPatcherTargetRunConfigurationProducer extends CidrTargetRunConfigurationProducer<CMakeConfiguration, CMakeTarget, CMakeAppRunConfiguration> {
    public CMakeRunPatcherTargetRunConfigurationProducer() {
        super(CMakeRunPatcherAppRunConfigurationType.getInstance(), CMakeTargetRunConfigurationBinder.INSTANCE);
    }
}
