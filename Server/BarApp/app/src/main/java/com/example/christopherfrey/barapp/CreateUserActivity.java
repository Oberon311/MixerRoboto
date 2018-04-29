package com.example.christopherfrey.barapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;
import android.widget.CompoundButton;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView lastNameValue;
    private TextView firstNameValue;
    private TextView birthDateValue;
    private TextView expireDateValue;
    private TextView addressValue;
    private TextView cityValue;
    private TextView stateValue;
    private TextView sexValue;
    private TextView heightValue;
    private TextView weightValue;
    private DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
    private Date dBirthDate;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_user);
        View YesBtn = findViewById(R.id.yesBtn);
        YesBtn.setVisibility(View.INVISIBLE);

        lastNameValue = findViewById(R.id.lnameScanTxtVw);
        firstNameValue = findViewById(R.id.fnameScanTxtVw);
        birthDateValue = findViewById(R.id.bdayScanTxtVw);
        expireDateValue = findViewById(R.id.expDateScanTxtVw);
        addressValue = findViewById(R.id.addressScanTxtVw);
        cityValue = findViewById(R.id.cityScanTxtVw);
        stateValue = findViewById(R.id.stateScanTxtVw);
        sexValue = findViewById(R.id.sexScanTxtVw);
        heightValue = findViewById(R.id.heightScanTxtVw);
        weightValue = findViewById(R.id.weightScanTxtVw);


        findViewById(R.id.scanBtn).setOnClickListener(this);
        Button yesBtn = (Button)findViewById(R.id.yesBtn);
        Button scanBtn = (Button)findViewById(R.id.scanBtn);

      //  if (getIntent().getStringExtra("EMAIL").isEmpty() == false)
       //     scanBtn.setText("Scan ID for " + getIntent().getStringExtra("EMAIL"));

        //if user chooses YES:
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUserActivity.this, OrderDrinkActivity.class);

                new createUser().execute(getIntent().getStringExtra("EMAIL"),
                                            lastNameValue.getText().toString(),
                                            firstNameValue.getText().toString(),
                                            birthDateValue.getText().toString(),
                                            expireDateValue.getText().toString(),
                                            addressValue.getText().toString(),
                                            cityValue.getText().toString(),
                                            stateValue.getText().toString(),
                                            sexValue.getText().toString(),
                                            heightValue.getText().toString(),
                                            weightValue.getText().toString());

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
                    Button YesBtn = findViewById(R.id.yesBtn);
                    YesBtn.setVisibility(View.VISIBLE);
                    try {
                        dBirthDate = df.parse((String) birthDateValue.getText());
                        long diff = (System.currentTimeMillis() - dBirthDate.getTime())/(1000*60*60*24);
                        if(diff < 7665) {
                            YesBtn.setText("Under 21");
                            YesBtn.setBackgroundColor(Color.RED);
                            YesBtn.setClickable(false);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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
        String sex = lines[14].substring(3,lines[14].length()).equals("1") ? "M" : "F";
        lastNameValue.setText(lines[2].substring(3,lines[2].length()));
        firstNameValue.setText(lines[4].substring(3,lines[4].length()));
        addressValue.setText(lines[17].substring(3,lines[17].length()));
        cityValue.setText(lines[18].substring(3,lines[18].length()));
        stateValue.setText(lines[19].substring(3,lines[19].length()));
        sexValue.setText(sex);
        expireDateValue.setText(expireDate);
        birthDateValue.setText(birthDate);
        heightValue.setText(lines[15].substring(3,lines[15].length()));
        weightValue.setText(lines[23].substring(3,lines[23].length()));
    }
}


