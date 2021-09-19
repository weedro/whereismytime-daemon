package com.weedro.whereismytime.daemon.util;

import java.util.Locale;

public final class OSUtils {

    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    public static OSType getOSType() {
        String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((osName.contains("mac")) || (osName.contains("darwin"))) {
            return OSType.MacOS;
        } else if (osName.contains("win")) {
            return OSType.Windows;
        } else if (osName.contains("nux")) {
            return OSType.Linux;
        } else {
            return OSType.Other;
        }
    }
}
