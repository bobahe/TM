package ru.levin.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ITerminalService {
    void println(@Nullable final String text);
    void printerr(@Nullable final String text);
    @NotNull String getLine();
}
