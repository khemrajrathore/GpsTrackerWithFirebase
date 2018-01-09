package com.buckleup.gpstracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Intent intent = getIntent();
    }

    public void forgetPassword(View view)
    {

        Toast.makeText(this,"Please Wait...",Toast.LENGTH_LONG).show();
        final EditText ephonenumber = (EditText) findViewById(R.id.forgetnumber);
        final EditText eemail = (EditText) findViewById(R.id.forgetemail);

        final String phonenumber = ephonenumber.getText().toString();
        final String email = eemail.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        String password = jsonResponse.getString("password");
                        String retemail = jsonResponse.getString("email");
                        Log.d("Retrieved email",retemail);
                        if(retemail.equals(email))
                            sendEmail(email,password);
                        else
                            showPassword("Please enter Valid email");
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
                        builder.setMessage("Enter Valid phonenumber and email").setNegativeButton("Retry", null).create().show();
                    }

                } catch (Exception e) {
                    //  Log.e("ksldjfklsdj","kldjfklsj");
                    // displayToast(response);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgetPassword.this);
                    builder1.setMessage("Please try again..").setNegativeButton("Retry", null).create().show();
                    e.printStackTrace();

                }
            }
        };

        ForgetPasswordRequest signinRequest = new ForgetPasswordRequest(phonenumber,responseListener);
        RequestQueue queue = Volley.newRequestQueue(ForgetPassword.this);
        queue.add(signinRequest);

    }

    public void sendEmail(String email,String password)
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                            startSignIn();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
                        builder.setMessage("Some network issue").setNegativeButton("Retry", null).create().show();
                    }

                } catch (Exception e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgetPassword.this);
                    builder1.setMessage("Please try again").setNegativeButton("Retry", null).create().show();
                    e.printStackTrace();

                }
            }
        };

        SendEmail signinRequest = new SendEmail(email,password,responseListener);
        RequestQueue queue = Volley.newRequestQueue(ForgetPassword.this);
        queue.add(signinRequest);
    }
    public void startSignIn()
    {
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
    }

    public void showPassword(String password)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.otpdialog);
        dialog.setTitle("Your password");
        TextView editText = (TextView) dialog.findViewById(R.id.otp_text);
        editText.setText(password);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
              // Intent intent = new Intent(ForgetPassword.this, SignIn.class);
               // startActivity(intent);
            }
        });
        dialog.show();

    }
    public void display()
    {
        Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();

    }
}
