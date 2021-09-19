package com.weedro.whereismytime.daemon;

import com.weedro.whereismytime.daemon.entity.Process;
import com.weedro.whereismytime.daemon.entity.TrackedWindow;
import com.weedro.whereismytime.daemon.entity.Window;
import com.weedro.whereismytime.daemon.service.WindowProvider;
import java.util.Map;
import java.util.Objects;
import java.util.TimerTask;

public class TrackTask extends TimerTask {
    private final WindowProvider activeWindowProvider;

    private final Map<String, TrackedWindow> trackedWindows;

    public TrackTask(WindowProvider activeWindowProvider, Map<String, TrackedWindow> trackedWindows) {
        this.activeWindowProvider = activeWindowProvider;
        this.trackedWindows = trackedWindows;
    }

    @Override
    public void run() {
        String id = activeWindowProvider.getActiveWindowId();
        Window window;
        if (Objects.equals(id, "0x0")) {
            window = new Window("None", id, Process.PROCESS_NONE);
        }
        else window = activeWindowProvider.getWindow(id);

        if (trackedWindows.containsKey(id)) {
            TrackedWindow trackedWindow = trackedWindows.get(id);
            trackedWindow.setWindow(window);
            trackedWindow.incrementSecondsWasted();
            trackedWindows.put(id, trackedWindow);
        }
        else {
            trackedWindows.put(id, new TrackedWindow(window));
        }
    }
}
