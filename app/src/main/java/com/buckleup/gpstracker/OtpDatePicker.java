package com.buckleup.gpstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.StringTokenizer;

public class OtpDatePicker extends AppCompatActivity {

    String phonenumber;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_date_picker);

        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("phonenumber");
        uid = intent.getStringExtra("uid");

    }

    public void getDate(View view){

        DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
        Welcome.dday = datePicker.getDayOfMonth();
        Welcome.dmonth = datePicker.getMonth()+1;
        Welcome.dyear = datePicker.getYear();
        Log.d("Date",""+Welcome.dday +" "+Welcome.dmonth +" "+Welcome.dyear);

        finish();
        Intent intent = new Intent(this,OtpTimePicker.class);
        intent.putExtra("phonenumber",phonenumber);
        intent.putExtra("uid",uid);
        startActivity(intent);

    }
}
