package com.danikoza.getpermissiondemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.danikoza.getpermission.DialogListener;
import com.danikoza.getpermission.GrantMe;
import com.danikoza.getpermission.ResponseListener;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private MaterialButton main_BTN_requestPermissions, main_BTN_requestPermissionsWithForce, main_BTN_requestPermissionsWithDialog;
    private GrantMe grantMe;
    private final String TAG = "pttt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grantMe = new GrantMe(this);
        grantMe.setDebug(true);

        initViews();
        initListeners();
    }

    /**
     * Must override methods to bind them to the permission manager
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        grantMe.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        grantMe.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Binding the buttons
     */
    private void initViews() {
        main_BTN_requestPermissions = findViewById(R.id.main_BTN_requestPermissions);
        main_BTN_requestPermissionsWithForce = findViewById(R.id.main_BTN_requestPermissionsWithForce);
        main_BTN_requestPermissionsWithDialog = findViewById(R.id.main_BTN_requestPermissionsWithDialog);
    }

    /**
     * Buttons listeners
     */
    private void initListeners() {
        main_BTN_requestPermissions.setOnClickListener(e -> onRequestPermissions());
        main_BTN_requestPermissionsWithForce.setOnClickListener(e -> onRequestPermissionsWithForce());
        main_BTN_requestPermissionsWithDialog.setOnClickListener(e -> onRequestPermissionsWithDialog());
    }

    /**
     * Permission requests
     */
    private void onRequestPermissions() {
        grantMe.requestPermissions(new String[]{Manifest.permission.CAMERA}, listener1);
    }

    private void onRequestPermissionsWithForce() {
        grantMe.requestPermissionsWithForce(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,} , listener2,"need permission for testing the functions" ,  new DialogListener() {
            @Override
            public void onPositiveButton() {
                Log.d(TAG, "Agreed");
            }

            @Override
            public void onNegativeButton() {
                Log.d(TAG, "Declined");
            }
        });
    }

    private void onRequestPermissionsWithDialog() {
        grantMe.requestPermissionsWithDialog(new String[]{ Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR,
                        Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS,},listener2,
                "Permission needed", "need permission for testing these functions", new DialogListener() {
                    @Override
                    public void onPositiveButton() {
                        Log.d(TAG, "Agreed");
                    }

                    @Override
                    public void onNegativeButton() {
                        Log.d(TAG, "Declined");
                    }
                });
    }


    /**
     * ResponseListeners on permissions requests
     */
    private final ResponseListener listener1 = new ResponseListener() {
        @Override
        public void onGranted(boolean allGranted) {
            Log.d(TAG, "onGranted = " + allGranted);
        }

        @Override
        public void onNotGranted(String[] permissions) {
            Log.d(TAG, "onNotGranted");
            for (String permission : permissions) {
                Log.d(TAG, permission);
            }
        }

        @Override
        public void onNeverAskAgain(String[] permissions) {
            Log.d(TAG, "onNeverAskAgain");
            grantMe.askPermissionsFromSetting("give me permissions", permissions, new DialogListener() {
                @Override
                public void onPositiveButton() {
                    Log.d(TAG, "onReTry");
                }

                @Override
                public void onNegativeButton() {
                    Log.d(TAG, "onImSure");
                }
            });
        }
    };

    private final ResponseListener listener2 = new ResponseListener() {
        @Override
        public void onGranted(boolean allGranted) {
            Log.d(TAG, "onGranted = " + allGranted);
        }

        @Override
        public void onNotGranted(String[] permissions) {
            Log.d(TAG, "onNotGranted");
            for (String permission : permissions) {
                Log.d(TAG, permission);
            }
        }

        @Override
        public void onNeverAskAgain(String[] permissions) {
            Log.d("pttt", "onNeverAskAgain");
        }
    };

}