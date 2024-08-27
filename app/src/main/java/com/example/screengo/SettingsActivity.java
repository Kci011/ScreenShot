package com.example.screengo;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class SettingsActivity extends AppCompatActivity {

    private Switch switchEnableScreenshots;
    private Spinner spinnerScreenshotQuality;
    private EditText edittextSaveDirectory;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Settings");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        switchEnableScreenshots = findViewById(R.id.switch_enable_screenshots);
        spinnerScreenshotQuality = findViewById(R.id.spinner_screenshot_quality);
        edittextSaveDirectory = findViewById(R.id.edittext_save_directory);

        // Load saved preferences
        loadPreferences();
    }

    private void loadPreferences() {
        // Use SharedPreferences to load settings and set UI elements
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        switchEnableScreenshots.setChecked(sharedPreferences.getBoolean("enable_screenshots", true));
        // Set spinner and edit text with saved values
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save preferences when the activity is paused
        savePreferences();
    }

    private void savePreferences() {
        PreferenceManager PreferenceManager = null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("enable_screenshots", switchEnableScreenshots.isChecked());
        // Save spinner and edit text values
        editor.apply();
    }
}