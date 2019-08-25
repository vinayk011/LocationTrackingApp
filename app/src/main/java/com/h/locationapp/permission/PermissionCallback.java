package com.h.locationapp.permission;


public interface PermissionCallback {
    void onPermissionResult(boolean granted, boolean neverAsk);
}