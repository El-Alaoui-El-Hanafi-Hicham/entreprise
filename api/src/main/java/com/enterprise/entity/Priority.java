package com.enterprise.entity;

public enum Priority {
    Low("LOW"),
    Medium("MEDIUM"),
    High("HIGH"),
    Critical("CRITICAL");

    private final String value;

    Priority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
