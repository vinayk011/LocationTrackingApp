package com.h.locationapp.permission;

import android.Manifest;


public enum Permission {


    FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION);

    String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public static Permission stringToPermission(String stringPermission) {
        for (Permission permission : Permission.values()) {
            if (stringPermission.equalsIgnoreCase(permission.toString()))
                return permission;
        }

        return null;
    }

    @Override
    public String toString() {
        return permission;
    }
}