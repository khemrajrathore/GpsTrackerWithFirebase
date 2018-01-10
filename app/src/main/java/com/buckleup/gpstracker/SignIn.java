package com.buckleup.gpstracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SignIn extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Information info = new Information();
    String uid = "";


    String name;
    String email;
    String phonenumber;
    String otp;
    Long otpvalidity ;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("signin", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("signin", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }

    public void openRegister(View view) {
        Toast.makeText(this,"Please Wait...",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    public void openWelcome(View view) {

        Toast.makeText(this,"Please Wait...",Toast.LENGTH_SHORT).show();
        EditText ephonenumber = (EditText) findViewById(R.id.logphnumber);//This is email
        EditText epassword = (EditText) findViewById(R.id.logpassword);
        final String vemail = ephonenumber.getText().toString(); //this is email
        password = epassword.getText().toString();

        mAuth.signInWithEmailAndPassword(vemail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Log.d("signin", "signInWithEmail:onComplete:" + task.isSuccessful());
                            Toast.makeText(getApplicationContext(),"SignIn Successfull",Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getApplicationContext(),Welcome.class));

                            //Firebase changes

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if (user != null) {
                                uid = user.getUid();
                            }



                            myRef = myRef.child("Register").child(uid);
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.

                                    info  = dataSnapshot.getValue(Information.class);

                                    phonenumber = info.getPhonenumber();
                                    email = info.getEmail();
                                    otp = info.getOtp();
                                    name = info.getName();
                                    otpvalidity = info.getOtpvalidity();
                                    Log.d("signindata","Value of name is : "+info.getPhonenumber());
                                    Log.d("readsuccess", "Value is: " + phonenumber + email + otp + name+"   "+otpvalidity +" "+otpvalidity.getClass().getName());

                                    otpvalidityupdate();
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w("databasereaderrror", "Failed to read value.", error.toException());
                                }
                            });



                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        else {
                            Log.w("signin", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getApplicationContext(), "SignIn Failed..",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

        /*Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                           String email = jsonResponse.getString("email");
                          String otp = jsonResponse.getString("otp");
                        String name = jsonResponse.getString("name");
                        Long otpvalidity = jsonResponse.getLong("otpvalidity");


                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                        long milliSeconds= otpvalidity;
                        Log.d("Milliseconds",milliSeconds+"");

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(milliSeconds);
                        Log.d("Time",formatter.format(calendar.getTime()));
                        Welcome.otpvalidity  = formatter.format(calendar.getTime());



                        Intent intent1 = new Intent(SignIn.this,LocationService.class);
                        intent1.putExtra("phonenumber",phonenumber);
                        //display(phonenumber);
                        startService(intent1);

                        Intent intent = new Intent(SignIn.this, Welcome.class);
                        intent.putExtra("phonenumber", phonenumber);
                        intent.putExtra("name",name);
                        intent.putExtra("password", password);
                        intent.putExtra("email",email);
                        intent.putExtra("otp",otp);

                        SignIn.this.startActivity(intent);
                        EditText ephonenumber =(EditText)findViewById(R.id.logphnumber);
                        EditText epassword = (EditText)findViewById(R.id.logpassword);

                        epassword.setText("");
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
                        builder.setMessage("Login Failed").setNegativeButton("Retry", null).create().show();
                    }

                } catch (Exception e) {
                    //  Log.e("ksldjfklsdj","kldjfklsj");
                    // displayToast(response);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SignIn.this);
                    builder1.setMessage("Check Your Internet Connection").setNegativeButton("Retry", null).create().show();
                    e.printStackTrace();

                }
            }
        };

        SigninRequest signinRequest = new SigninRequest(phonenumber, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SignIn.this);
        queue.add(signinRequest);*/


    }

    public void otpvalidityupdate(){
        //Long otpvalidity = jsonResponse.getLong("otpvalidity");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Log.d("otpvalidity",""+info.getPhonenumber());
        long milliSeconds= otpvalidity;
        Log.d("Milliseconds",milliSeconds+"");

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(milliSeconds);
        Log.d("Time",formatter.format(calendar.getTime()));
        Welcome.otpvalidity  = formatter.format(calendar.getTime());


        Intent intent1 = new Intent(SignIn.this,LocationService.class);
        intent1.putExtra("phonenumber", phonenumber);
        intent1.putExtra("uid",uid);
        //display(phonenumber);
        startService(intent1);

        Intent intent = new Intent(SignIn.this, Welcome.class);
        intent.putExtra("phonenumber", phonenumber);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        intent.putExtra("email", email);
        intent.putExtra("otp", otp);
        intent.putExtra("uid",uid);


        SignIn.this.startActivity(intent);
        EditText ephonenumber =(EditText)findViewById(R.id.logphnumber);
        EditText epassword = (EditText)findViewById(R.id.logpassword);

        epassword.setText("");


    }

    public void display(String phonenumber)
    {
        Toast.makeText(this,"Phonenumber "+phonenumber,Toast.LENGTH_LONG);
    }
   public void forgetPassword(View view)
   {
       Intent intent = new Intent(this,ForgetPassword.class);
       startActivity(intent);
   }


}

