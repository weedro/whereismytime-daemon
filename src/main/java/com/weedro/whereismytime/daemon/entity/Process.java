package com.weedro.whereismytime.daemon.entity;

public record Process (String name, int pid) {
    public static final Process PROCESS_NONE = new Process("None", -1);
}
