package com.lucky7.parky.core.entity;

import androidx.annotation.NonNull;

public enum HistoryStatus {
    IN("IN"),
    OUT("OUT");

    private final String status;

    HistoryStatus(String status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return status;
    }

    public static HistoryStatus fromString(String text) {
        for (HistoryStatus status : HistoryStatus.values()) {
            if (status.status.equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }
}
