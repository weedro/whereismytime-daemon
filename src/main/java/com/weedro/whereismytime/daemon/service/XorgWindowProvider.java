package com.weedro.whereismytime.daemon.service;

import com.weedro.whereismytime.daemon.entity.Process;
import com.weedro.whereismytime.daemon.entity.Window;
import com.weedro.whereismytime.daemon.util.IOUtils;
import com.weedro.whereismytime.daemon.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class XorgWindowProvider implements WindowProvider {

    public String getActiveWindowId() {
        String s = IOUtils.executeCommand("xprop", "-root", "_NET_ACTIVE_WINDOW");
        return s
                .split("#")[1]
                .trim();
    }

    public Window getWindow(String id) {
        String output = IOUtils.executeCommand("xprop", "-id", id);

        Map<String, String> map = new HashMap<String, String>();

        output.lines().forEach(line -> StringUtils.splitDelimiters(line, 2, ":", "=")
                .filter(kv -> kv.length == 2)
                .ifPresent(kv -> map.put(
                        kv[0].trim(),
                        kv[1].trim().replace("\"", ""))
                )
        );

        Process owner = new Process(
                map.get("WM_CLASS(STRING)").split(",")[1],
                Integer.parseInt(map.get("_NET_WM_PID(CARDINAL)"))
        );

        return new Window(
                map.get("_NET_WM_NAME(UTF8_STRING)"),
                id,
                owner
        );
    }
}
