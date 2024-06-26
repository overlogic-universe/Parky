package com.lucky7.parky.core.entity;

import androidx.annotation.NonNull;

public enum ParkStatus {
    PARKED("parked"),
    NOT_PARKED("not parked");

    private final String status;

    ParkStatus(String status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return status;
    }

    public static ParkStatus fromString(String text) {
        for (ParkStatus status : ParkStatus.values()) {
            if (status.status.equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }
}
