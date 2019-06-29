package ru.levin.tm.command.system;

import com.jcabi.manifests.Manifests;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;

public final class AboutCommand extends AbstractCommand {
    public AboutCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "about";
        this.description = "Show info about app";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void execute() {
        final String buildNumber = Manifests.read("Build-Number");
        System.out.println("[ABOUT APPLICATION]");
        System.out.println(buildNumber);
    }
}
