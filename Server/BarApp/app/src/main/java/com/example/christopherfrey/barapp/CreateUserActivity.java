package com.example.christopherfrey.barapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;
import android.widget.CompoundButton;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView lastNameValue;
    private TextView firstNameValue;
    private TextView birthDateValue;
    private TextView expireDateValue;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        lastNameValue = findViewById(R.id.lnameScanTxtVw);
        firstNameValue = findViewById(R.id.fnameScanTxtVw);
        birthDateValue = findViewById(R.id.bdayScanTxtVw);
        expireDateValue = findViewById(R.id.expDateScanTxtVw);

        findViewById(R.id.scanBtn).setOnClickListener(this);
        Button yesBtn = (Button)findViewById(R.id.yesBtn);
        Button scanBtn = (Button)findViewById(R.id.scanBtn);

        if (getIntent().getStringExtra("EMAIL").isEmpty() == false)
            scanBtn.setText("Scan ID for " + getIntent().getStringExtra("EMAIL"));

        //if user chooses YES:
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUserActivity.this, OrderDrinkActivity.class);
                new createUser().execute(getIntent().getStringExtra("EMAIL"));
                intent.putExtra("EMAIL", getIntent().getStringExtra("EMAIL"));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scanBtn) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

        /*else if (v.getId() == R.id.yesBtn) {
            // launch barcode activity.
            Intent intent = new Intent(this, OrderDrinkActivity.class);

            startActivity(new Intent(this, OrderDrinkActivity.class));
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //statusMessage.setText(R.string.barcode_success);
                    parseBarcode(barcode.rawValue);
                    Log.d(TAG, "Barcode read: " + barcode.rawValue);
                } else {
                    //statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //statusMessage.setText(String.format(getString(R.string.barcode_error),
                //      CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void parseBarcode(String str) {
        String[] lines = str.split("\\r?\\n");
        String birthDate = lines[12].substring(3,5) + '-' + lines[12].substring(5, 7) + '-' + lines[12].substring(7, 11);
        String expireDate = lines[13].substring(3,5) + '-' + lines[13].substring(5, 7) + '-' + lines[13].substring(7, 11);
        lastNameValue.setText(lines[2].substring(3,lines[2].length()));
        firstNameValue.setText(lines[4].substring(3,lines[4].length()));
        expireDateValue.setText(expireDate);
        birthDateValue.setText(birthDate);
    }
}
