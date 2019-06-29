package ru.levin.tm;

import ru.levin.tm.console.Bootstrap;

public final class Application {
    public static void main(String[] args) {
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.init();
    }
}
