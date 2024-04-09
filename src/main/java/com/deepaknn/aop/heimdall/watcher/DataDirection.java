package com.deepaknn.aop.heimdall.watcher;

public enum DataDirection {

    INPUT("Input"),
    OUTPUT("Output");

    private final String value;

    DataDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
