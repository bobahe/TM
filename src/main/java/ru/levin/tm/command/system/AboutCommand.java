package ru.levin.tm.command.system;

import com.jcabi.manifests.Manifests;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class AboutCommand extends AbstractCommand {
    private final ITerminalService terminalService;

    public AboutCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Show info about app";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    public void execute() {
        final String buildDeveloper = Manifests.read("Build-Developer");
        final String buildVersion = Manifests.read("Build-Version");
        final String buildNumber = Manifests.read("Build-Number");
        terminalService.println("[ABOUT APPLICATION]");
        terminalService.println(buildDeveloper);
        terminalService.println(buildVersion);
        terminalService.println(buildNumber);
    }
}
