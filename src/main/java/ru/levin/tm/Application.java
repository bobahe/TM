package ru.levin.tm;

import ru.levin.tm.console.TerminalCommandRunner;

public class Application {
    public static void main(String[] args) {
        TerminalCommandRunner.getInstance().process();
    }
}
