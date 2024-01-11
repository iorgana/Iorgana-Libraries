package com.iorgana.iorganalibraries.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.iorgana.iorganalibraries.databinding.ActivityMainBinding;
import com.iorgana.iorganalibraries.test_alertmaker.AlertMakerActivity;
import com.iorgana.iorganalibraries.test_droidhelpers.DroidHelpersActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        /**
         * Test Alert Maker
         * -----------------------------------------------------------------------
         * - Alert Maker lets you create beautiful alerts like bootstrap
         */
        binding.btnAlertMaker.setOnClickListener(view->{
            startActivity(new Intent(this, AlertMakerActivity.class));
        });


        /**
         * Test Droid Helpers
         * -----------------------------------------------------------------------
         */
        binding.btnDroidHelpers.setOnClickListener(view->{
            startActivity(new Intent(this, DroidHelpersActivity.class));
        });



    }
}