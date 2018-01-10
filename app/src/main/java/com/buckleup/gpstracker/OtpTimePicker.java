package com.buckleup.gpstracker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class OtpTimePicker extends AppCompatActivity {


    String phonenumber;
    long millisec;
    String myDate = null;
    String uid ;
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_time_picker);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        phonenumber = intent.getStringExtra("phonenumber");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getTime(View view) throws ParseException {

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        Welcome.thour = timePicker.getHour();
        Welcome.tmin = timePicker.getMinute();
        myDate = Welcome.dyear+"/"+Welcome.dmonth+"/"+Welcome.dday+" "+Welcome.thour+":"+
        Welcome.tmin+":"+"00";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(myDate);
        millisec = date.getTime();

        otpConfirmation();
    }
        //finish();
    public void otpConfirmation()
    {
        String str="";
        Random rand = new Random();
        for(int i=0;i<6;i++)
        {
            str+=rand.nextInt(9);
        }
        otp = str;
        /*Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        //The otp is stored successfully

                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OtpTimePicker.this);
                        builder.setMessage("Otp not stored").setNegativeButton("Retry",null).create().show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        otpupdaterequest registerRequest = new otpupdaterequest(phonenumber,str,millisec+"",responseListener);
        RequestQueue queue = Volley.newRequestQueue(OtpTimePicker.this);
        queue.add(registerRequest);*/



        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.otpdisplay);
        dialog.setTitle("My OTP");
        TextView myotp = (TextView) dialog.findViewById(R.id.otpdisplay_otp);
        myotp.setText(str);
        TextView myvalidity = (TextView) dialog.findViewById(R.id.otpdisplay_validity);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        final long milliSeconds= millisec;
        Log.d("Milliseconds",milliSeconds+"");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Log.d("Time",formatter.format(calendar.getTime()));
        String str1 = formatter.format(calendar.getTime());
        myvalidity.setText("Validity : " +str1);
        Welcome.otpvalidity = str1;

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

                Log.d("otp",otp + milliSeconds+"   "+uid);
                myRef = myRef.child("Register").child(uid);
                myRef.child("otp").setValue(otp);
                myRef.child("otpvalidity").setValue(millisec);
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
        Welcome.otp = str;
        Log.d("Date",""+Welcome.dday +" "+Welcome.dmonth +" "+Welcome.dyear);
        Log.d("time",""+Welcome.thour +" "+Welcome.tmin);
        Log.d("OTP",str);
        Log.d("Current Time",System.currentTimeMillis()+"");
        Log.d("Mymilli",""+millisec);

    }

}

