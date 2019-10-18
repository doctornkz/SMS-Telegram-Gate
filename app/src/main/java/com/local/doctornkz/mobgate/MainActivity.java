package com.local.doctornkz.mobgate;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_RECEIVE_SMS = 0;
    Parameters parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        requestPermission(Manifest.permission.RECEIVE_SMS, PERMISSIONS_REQUEST_RECEIVE_SMS);

        Log.d(parameters.TAG, "Created step");
        Intent sendIntent = new Intent();
        Context context = this;

        // TODO: Check SIM card
        /*
        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int simStateMain = telMgr.getSimState(0);
        int simStateSecond = telMgr.getSimState(1);
        Log.d(TAG, "SimState Main:" +  String.valueOf(simStateMain));
        Log.d(TAG, "SimState Second:" +  String.valueOf(simStateSecond));
        /5 = OK
  */
        // TODO: Disable gate
        SmsListener smsListener = new SmsListener();
        smsListener.onReceive(context, sendIntent);
        Log.d(parameters.TAG, "Created step, exit");

    }

    private void requestPermission(String permission, int requestCode) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(this, "Granting permission is necessary!", Toast.LENGTH_LONG).show();
                Log.d(parameters.TAG, "Request permissions step");

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{permission},
                        requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_RECEIVE_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Granting permission : Success", Toast.LENGTH_LONG).show();
                    Log.d(parameters.TAG, "Result permission request step, success");
                } else {
                    Toast.makeText(this, "Granting permission : Declined", Toast.LENGTH_LONG).show();
                    Log.d(parameters.TAG, "Result permission request step, decline");
                }
            }
        }
    }


    public void onStart()  {
        super.onStart();

        Button button = findViewById(R.id.testMessage);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new TelegramNotifier().execute("Mobile phone connected", parameters.TELEGRAMID, parameters.BOTID, parameters.API);
            }
        });
        String Message = "TgID:" + parameters.TELEGRAMID;

        TextView textView = findViewById(R.id.myId);

        textView.setText(Message);

/*
        final Switch switchGate = findViewById(R.id.switchGate);


        switchGate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, String.valueOf(isChecked));

            }
        });

        */

        Log.d(parameters.TAG, "onStart:Outbound from switch.");

    }

}



