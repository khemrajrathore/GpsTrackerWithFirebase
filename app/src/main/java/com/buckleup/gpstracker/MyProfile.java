package com.buckleup.gpstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        String name = null;
        String email = null;
        String number = null;
        String password = null;
        String otp = null;

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        number = intent.getStringExtra("phonenumber");
        password = intent.getStringExtra("password");
        otp = intent.getStringExtra("otp");

        TextView tname = (TextView)findViewById(R.id.textViewname);
        TextView temail = (TextView)findViewById(R.id.textViewemail);
        TextView tnumber = (TextView)findViewById(R.id.textViewnumber);
        TextView totp = (TextView)findViewById(R.id.textViewotp);

        tname.setText(name);
        temail.setText(email);
        tnumber.setText(number);
        totp.setText(otp);

    }

}
