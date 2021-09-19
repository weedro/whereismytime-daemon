package com.weedro.whereismytime.daemon.exception;

public class CommandExecutionException extends RuntimeException {
    public CommandExecutionException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}