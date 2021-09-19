package com.weedro.whereismytime.daemon.util;

import com.weedro.whereismytime.daemon.exception.CommandExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public final class IOUtils {
    private IOUtils() {

    }

    public static String executeCommand(String... command) {
        try {
            InputStream inputStream = new ProcessBuilder()
                    .command(command)
                    .start()
                    .getInputStream();

            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new CommandExecutionException("Failed to execute command: ", e);
        }
    }
}
