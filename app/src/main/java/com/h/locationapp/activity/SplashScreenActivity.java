package com.h.locationapp.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.h.locationapp.R;
import com.h.locationapp.constants.IntentConstants;
import com.h.locationapp.util.AppUtil;

import androidx.annotation.RequiresApi;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannels();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel connectionChannel = new NotificationChannel(IntentConstants.NOTIFY_CHANNEL_ID,
                IntentConstants.NOTIFY_CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN);
        connectionChannel.enableLights(false);
        connectionChannel.enableVibration(false);
        connectionChannel.setShowBadge(false);
        connectionChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(connectionChannel);

    }

    @Override
    protected void onResume() {
        super.onResume();
        goNext();
    }

    /**
     * Step 1: Check Google Play services
     */
    private void goNext() {
        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {
            AppUtil.dashboard(this);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }

}
