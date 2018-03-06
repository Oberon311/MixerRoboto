package com.example.christopherfrey.testconnect;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField;
    private TextView status, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText)findViewById(R.id.editText1);

        status = (TextView)findViewById(R.id.textView6);
        role = (TextView)findViewById(R.id.textView7);
    }

    public void login(View view){
        String username = usernameField.getText().toString();
        new SigninActivity(this,status,role,0).execute(username);
    }

    public void loginPost(View view){
        String username = usernameField.getText().toString();
        new SigninActivity(this,status,role,1).execute(username);
    }
}
