package com.weedro.whereismytime.daemon.entity;

public class TrackedWindow {
    private Window window;
    private int secondsWasted = 0;

    public TrackedWindow(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }

    public int getSecondsWasted() {
        return secondsWasted;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public void incrementSecondsWasted() {
        this.secondsWasted++;
    }
}
