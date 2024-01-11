package com.iorgana.iorganalibraries.test_droidhelpers;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.iorgana.droidhelpers.network.AddressHelper;
import com.iorgana.droidhelpers.network.NetworkHelper;
import com.iorgana.droidhelpers.network.WifiHelper;
import com.iorgana.droidhelpers.notification.NotificationHelper;
import com.iorgana.iorganalibraries.R;
import com.iorgana.iorganalibraries.databinding.ActivityDroidHelpersBinding;
import com.iorgana.iorganalibraries.home.MainActivity;

public class DroidHelpersActivity extends AppCompatActivity {

    ActivityDroidHelpersBinding binding;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDroidHelpersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**
         * Get IP Address Of Device
         */
        binding.btnGetLocalAddress.setOnClickListener(view->{
            String ipV4 =  AddressHelper.getIPAddress(false);
            String ipV6 = AddressHelper.getIPAddress(true);
            String txt = "IP v4 is: "+ ipV4+"\nIP V6 is: "+ipV6;
            binding.txtAddress.setText(txt);
        });

        /**
         * IS Network Available
         */
        binding.btnIsNetworkAvailable.setOnClickListener(view->{
            String txt = "Is network available: "+ NetworkHelper.isConnected(this);
            binding.txtIsNetworkAvailable.setText(txt);
        });

        /**
         * IS Wifi Enabled
         */
        binding.btnIsWifiEnabled.setOnClickListener(view->{
            String txt = "Is wifi enabled: "+ WifiHelper.isWifiEnabled(getApplication());
            binding.txtIsWifiEnabled.setText(txt);
        });

        /**
         * IS Hotspot Enabled
         */
        binding.btnIsHotspotEnabled.setOnClickListener(view->{
            String txt = "Is hotspot enabled: "+ WifiHelper.isHotspotEnabled(getApplication());
            binding.txtIsHotspotEnabled.setText(txt);
        });

        /**
         * Create Notification
         * --------------------------------------------------------------------
         * Before create notification
         * Make sure to get notification permission for api 32 and later
         */
        binding.btnPostNotification.setOnClickListener(view->{
            prepareNotification(false);
        });


    }

    /**
     * Prepare Notification
     * --------------------------------------------------------------------
     * Before create notification
     * Make sure to get notification permission for api 32 and later
     */
     void prepareNotification(boolean isPermissionGranted){
         if(!isPermissionGranted){
             askNotificationPermission();
             return;
         }

         // Create Notification
         createNotification();
     }


    /**
     * Create Notification
     * --------------------------------------------------------------------
     * After notification permission is granted
     * Its time to create the notification
     */
    void createNotification(){
        // Create notification
        NotificationHelper notificationHelper = new NotificationHelper(this);
        notificationHelper.notificationTitle = "My Notification";
        notificationHelper.notificationText = "Notification is created successfully";
        notificationHelper.notification_icon = R.drawable.ic_notification;
        notificationHelper.setMainPendingIntent(getPendingIntentNotification());
        notificationHelper.setAction(0, "close", getPendingIntentNotification());
        Notification notification = notificationHelper.make();
        // Show notification
        notificationHelper.notificationManager.notify(NotificationHelper.notify_id, notification);

    }


    private void askNotificationPermission(){
        /**
         * - IF permission ok, return:
         * - IF permission not required for this api, OR permission already granted, Return true;
         */
        if (Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            prepareNotification(true);
            return;
        }
        /**
         * - IF permission never asked before, ask for permission
         */
        if(!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            return;
        }
        /**
         * - IF permission already asked once:
         * - explain to user why permission is required
         */
        if(shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){
            showDialogNotificationRequired();
        }
    }


    /**
     * Get Permission Results
     * ------------------------------------------------------------------------
     */
    // For permissions as String
    private final ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted->{
        if(isGranted){
            // Permission is granted. re-call startNetguardService.
            prepareNotification(true);
        }
        else{
            // Permission is denied:
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // IF notification denied at first time
                // Explain to user why notification permission is required
                // And ask for permission again
                if(shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){
                    askNotificationPermission();
                }
                // IF notification permission denied twice, the app can ask for it again
                // Show dialog that tells the user to enable notification from app settings
                else{
                    showDialogNotificationDenied();
                }
            }
        }
    });





    private PendingIntent getPendingIntentNotification(){
        int flag = (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) ? PendingIntent.FLAG_IMMUTABLE : 0;
        Intent intent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, intent, flag);
    }







    /**
     * Show Dialog Notification Required
     * --------------------------------------------------------------------------
     * - When notification denied at first time
     * - Explain to user why notification permission is required
     */
    private void showDialogNotificationRequired(){
        if(Build.VERSION.SDK_INT>=33) {
            new AlertDialog.Builder(context)
                    .setTitle("Permission Alert")
                    .setMessage("Notification permission is required to show notification")
                    .setPositiveButton(R.string.ask_again, (dialog, which) -> {
                        dialog.dismiss();
                        // Ask for permission
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                    })
                    .create()
                    .show();
        }
    }
    /**
     * Show Dialog Notification Denied
     * --------------------------------------------------------------------------
     * - When notification permission denied twice, the app can ask for it again
     * - Show dialog that tells the user to enable notification from app settings
     */
    private void showDialogNotificationDenied(){
        new AlertDialog.Builder(context)
                .setTitle("Permission Denied!")
                .setMessage("Unable to ask permission again because yu have denied it twice, please go to settings and enable permission.")
                .setPositiveButton(R.string.go_settings, (dialog, which) -> {
                    dialog.dismiss();
                    // Go settings to enable notifications
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.close, (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .create()
                .show();
    }

}