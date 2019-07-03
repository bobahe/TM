package ru.levin.tm.command.system;

import com.jcabi.manifests.Manifests;
import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class AboutCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public AboutCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "about";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Show info about app";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public void execute() {
        @NotNull final String buildDeveloper = Manifests.read("Build-Developer");
        @NotNull final String buildVersion = Manifests.read("Build-Version");
        @NotNull final String buildNumber = Manifests.read("Build-Number");
        terminalService.println("[ABOUT APPLICATION]");
        terminalService.println(buildDeveloper);
        terminalService.println(buildVersion);
        terminalService.println(buildNumber);
    }

}
