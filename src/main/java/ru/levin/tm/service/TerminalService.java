package ru.levin.tm.service;

import ru.levin.tm.api.service.ITerminalService;

import java.io.InputStreamReader;
import java.util.Scanner;

public class TerminalService implements ITerminalService {
    private final Scanner scanner = new Scanner(new InputStreamReader(System.in));

    public Scanner getScanner() {
        return scanner;
    }
}
