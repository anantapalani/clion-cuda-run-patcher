import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.util.PathUtil;
import com.jetbrains.cidr.cpp.execution.CLionRunConfigurationExtensionBase;
import com.jetbrains.cidr.execution.CidrRunConfiguration;
import com.jetbrains.cidr.lang.toolchains.CidrToolEnvironment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class CudaCLionRunConfigurationExtension extends CLionRunConfigurationExtensionBase {

    @Override
    protected void patchCommandLine(@NotNull CidrRunConfiguration cidrRunConfiguration, @Nullable RunnerSettings runnerSettings, @NotNull CidrToolEnvironment cidrToolEnvironment, @NotNull GeneralCommandLine generalCommandLine, @NotNull String s) throws ExecutionException {
        // check if we have encountered https://youtrack.jetbrains.com/issue/CPP-10292
        if (generalCommandLine.getExePath().endsWith(".o")) {
            String exePathString = PathUtil.getParentPath(PathUtil.getParentPath(PathUtil.getParentPath(generalCommandLine.getExePath())));
            if (generalCommandLine.getWorkDirectory().toString().startsWith(exePathString)) {
                generalCommandLine.setWorkDirectory(exePathString);
            }
            String correctedExePathString = exePathString + File.separator + cidrRunConfiguration.getTargetAndConfigurationData().target.targetName;
            generalCommandLine.setExePath(correctedExePathString);
        }
    }

    @Override
    protected void attachToProcess(@NotNull CidrRunConfiguration cidrRunConfiguration, @NotNull ProcessHandler processHandler, @NotNull CidrToolEnvironment cidrToolEnvironment, @Nullable RunnerSettings runnerSettings) {
    }

    @Override
    protected boolean isApplicableFor(@NotNull CidrRunConfiguration cidrRunConfiguration) {
        return true;
    }

    @Override
    protected boolean isEnabledFor(@NotNull CidrRunConfiguration cidrRunConfiguration, @Nullable RunnerSettings runnerSettings) {
        return true;
    }
}
