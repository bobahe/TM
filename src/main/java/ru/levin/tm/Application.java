package ru.levin.tm;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.console.Bootstrap;

public final class Application {
    public static void main(String[] args) {
        @NotNull final Bootstrap bootstrap = new Bootstrap();
        bootstrap.init();
    }
}
