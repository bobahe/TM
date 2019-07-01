package ru.levin.tm.service;

import ru.levin.tm.api.service.ITerminalService;

import java.io.InputStreamReader;
import java.util.Scanner;

public class TerminalService implements ITerminalService {
    private final Scanner scanner = new Scanner(new InputStreamReader(System.in));

    @Override
    public void println(final String text) {
        System.out.println(text);
    }

    @Override
    public void printerr(final String text) {
        System.err.println(text);
    }

    @Override
    public String getLine() {
        return scanner.nextLine();
    }
}
