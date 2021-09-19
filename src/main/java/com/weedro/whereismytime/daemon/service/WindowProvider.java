package com.weedro.whereismytime.daemon.service;

import com.weedro.whereismytime.daemon.entity.Window;

public interface WindowProvider {
    String getActiveWindowId();
    Window getWindow(String id);
}
