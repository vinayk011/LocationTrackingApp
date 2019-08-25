package com.h.locationapp;

import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import com.h.locationapp.activity.BaseActivity;
import com.h.locationapp.databinding.ActivityMainBinding;
import com.h.locationapp.permission.Permission;
import com.h.locationapp.permission.PermissionCallback;
import com.h.locationapp.service.LocationTrackingService;

import static com.h.locationapp.constants.IntentConstants.ACTION_LOCATION_BROADCAST;
import static com.h.locationapp.constants.IntentConstants.EXTRA_LATITUDE;
import static com.h.locationapp.constants.IntentConstants.EXTRA_LONGITUDE;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setServiceRunning(LocationTrackingService.isRunning());
        binding.startLocation.setOnClickListener(this);
        binding.stopLocation.setOnClickListener(this);
    }


    public void start() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ACTION_LOCATION_BROADCAST));
    }

    public void resume() {
        checkPermissions();
    }

    public void stop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void checkPermissions() {
        requestPermission(Permission.FINE_LOCATION, new PermissionCallback() {
            @Override
            public void onPermissionResult(boolean granted, boolean neverAsk) {
                if (granted) {

                } else {
                    checkPermissions();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_location) {
            startLocationService();
        } else if (view.getId() == R.id.stop_location) {
            stopLocationService();
        }

    }

    private void startLocationService() {
        LocationTrackingService.startService(this);
        binding.setServiceRunning(LocationTrackingService.isRunning());
    }

    private void stopLocationService() {
        LocationTrackingService.stopService(this);
        binding.setServiceRunning(LocationTrackingService.isRunning());
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() == ACTION_LOCATION_BROADCAST) {
                String latitude = intent.getStringExtra(EXTRA_LATITUDE);
                String longitude = intent.getStringExtra(EXTRA_LONGITUDE);
                if (latitude != null && longitude != null) {
                    binding.msgView.setText(getString(R.string.location_service_started) + "\n Latitude : " + latitude + "\n Longitude: " + longitude);
                }
            }
        }
    };
}
