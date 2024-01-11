package com.iorgana.iorganalibraries.test_alertmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.iorgana.alertmaker.AlertMaker;
import com.iorgana.iorganalibraries.databinding.ActivityAlertMakerBinding;

public class AlertMakerActivity extends AppCompatActivity {

    ActivityAlertMakerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlertMakerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        /**
         * Create None Cancelable Alerts
         */

        // Alert Success
        LinearLayout alertSuccess = new AlertMaker(this)
                .setType(AlertMaker.AlertType.Success)
                .setTitle("Alert Success")
                .setContent("this is an example of an alert success")
                .setMarginTop(10)
                .build();

        // Alert Info
        LinearLayout alertInfo = new AlertMaker(this)
                .setType(AlertMaker.AlertType.Info)
                .setTitle("Alert Info")
                .setContent("this is an example of an alert info")
                .setMarginTop(10)
                .build();


        // Alert Info
        LinearLayout alertWarning = new AlertMaker(this)
                .setType(AlertMaker.AlertType.Warning)
                .setTitle("Alert Warning")
                .setContent("this is an example of an alert waring")
                .setMarginTop(10)
                .build();

        binding.boxOne.addView(alertSuccess);
        binding.boxOne.addView(alertInfo);
        binding.boxOne.addView(alertWarning);


        /**
         * Create Cancelable Alert
         * -----------------------------------------------------
         * By default, alert is not cancelable
         * To make it cancelable, set `setCancelable(true)`
         * This will show a close `x` icon
         */

        // Alert Danger
        LinearLayout alertDanger = new AlertMaker(this)
                .setType(AlertMaker.AlertType.Danger
                )
                .setTitle("Alert Danger")
                .setContent("this is an example of cancelable alert waring")
                .setMargins(10,5,0,0)
                .setCancelable(true)
                .build();

        binding.boxTwo.addView(alertDanger);


    }
}