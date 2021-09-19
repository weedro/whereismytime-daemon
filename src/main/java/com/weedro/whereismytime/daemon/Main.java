package com.weedro.whereismytime.daemon;
import com.weedro.whereismytime.daemon.entity.TrackedWindow;
import com.weedro.whereismytime.daemon.exception.UnauthorizedException;
import com.weedro.whereismytime.daemon.service.AuthenticationService;
import com.weedro.whereismytime.daemon.service.WindowProvider;
import com.weedro.whereismytime.daemon.service.XorgWindowProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: daemon <login> <password>");
            System.exit(1);
        }

        Timer timer = new Timer("Timer");

        try {
            String accessToken = AuthenticationService.login(args[0], args[1]);
            WindowProvider activeWindowProvider = new XorgWindowProvider();
            Map<String, TrackedWindow> trackedWindows = new HashMap<>();

            TimerTask trackTask = new TrackTask(activeWindowProvider, trackedWindows);
            TimerTask sendTask = new SendTask(trackedWindows, accessToken);

            timer.schedule(trackTask, 0, 1000);
            timer.schedule(sendTask, 60000, 60000);

        } catch (UnauthorizedException e) {
            System.out.println(e.getMessage());
        } finally {
            // todo: graceful shutdown
            //timer.cancel();
        }
    }
}
