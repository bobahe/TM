package ru.levin.tm.api.service;

public interface ITerminalService {
    void println(final String text);
    void printerr(final String text);
    String getLine();
}
