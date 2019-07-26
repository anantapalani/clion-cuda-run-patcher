import com.intellij.execution.configurations.ConfigurationFactory;
import com.jetbrains.cidr.cpp.cmake.model.CMakeConfiguration;
import com.jetbrains.cidr.cpp.cmake.model.CMakeTarget;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration;
import com.jetbrains.cidr.cpp.execution.CMakeTargetRunConfigurationBinder;
import com.jetbrains.cidr.execution.CidrTargetRunConfigurationProducer;
import org.jetbrains.annotations.NotNull;

// analogous to com.jetbrains.cidr.cpp.execution.CMakeTargetRunConfigurationProducer

public class CMakeRunPatcherTargetRunConfigurationProducer extends CidrTargetRunConfigurationProducer<CMakeConfiguration, CMakeTarget, CMakeAppRunConfiguration> {
    public CMakeRunPatcherTargetRunConfigurationProducer() {
        super(CMakeTargetRunConfigurationBinder.INSTANCE);
    }

    @NotNull
    public ConfigurationFactory getConfigurationFactory() {
        return CMakeRunPatcherAppRunConfigurationType.getInstance().getFactory();
    }
}
