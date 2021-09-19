package com.weedro.whereismytime.daemon;

import com.weedro.whereismytime.daemon.entity.TrackedWindow;
import com.weedro.whereismytime.daemon.service.ApiService;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class SendTask extends TimerTask {
    private final ApiService apiService;

    private final Map<String, TrackedWindow> previous = new HashMap<>();
    private final Map<String, TrackedWindow> trackedWindows;

    public SendTask(Map<String, TrackedWindow> trackedWindows, String accessToken) {
        this.trackedWindows = trackedWindows;
        this.apiService = new ApiService(accessToken);
    }

    @Override
    public void run() {
        System.out.println("sending...");

        // todo: count a delta
        //Map<String, TrackedWindow> delta = trackedWindows.values().stream()
        apiService.sendUpdates(trackedWindows);
    }
}
