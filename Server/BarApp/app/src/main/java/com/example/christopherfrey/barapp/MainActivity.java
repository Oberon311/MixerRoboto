package com.example.christopherfrey.barapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    private String gmail;
    private SignInButton SignIn;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;
    private TextView found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        SignIn = (SignInButton)findViewById(R.id.bn_login);
        found = (TextView)findViewById(R.id.found);
        SignIn.setOnClickListener(this);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }


    //Sets the OnClickListeners for the sign in and sign out buttons
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.bn_login:
                signIn();
                break;
        }
    }

    public void openNewActivity() {
        Intent intent;
        if (found.getText().toString().length() > 0){
            intent = new Intent(this, OrderDrinkActivity.class);
        }else {
            intent = new Intent(this, CreateUserActivity.class);
        }

        intent.putExtra("EMAIL", gmail);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Do nothing
    }

    //function that launches the GoogleSignIn API
    private void signIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);

    }

    //This is the function that handles the login. If they successfully log in, then their
    //email and name are pulled from the google account and displayed.
    //Since we only need the email, we can send them to the scanning activity after a successful
    // login passing the email as a string value
    private void handleView(GoogleSignInResult result){
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            gmail = account.getEmail();
            new findEmail(this).execute(gmail);

            openNewActivity();
        }
    }

    //Generic function that gets the results from google and sends the handleView function
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleView(result);
        }
    }
}