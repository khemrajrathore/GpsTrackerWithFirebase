package com.buckleup.gpstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Admin on 2/15/2017.
 */

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("sigin", "onAuthStateChanged:signed_in:" + user.getUid());
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

    public void openregisterrequest(View view)
    {
        EditText ephonenumber = (EditText)findViewById(R.id.regphnumber);
        EditText epassword = (EditText)findViewById(R.id.regpassword);
        EditText ename = (EditText)findViewById(R.id.regname);
        EditText eemail = (EditText)findViewById(R.id.regemail);

        final String fname = ename.getText().toString();
        String email = eemail.getText().toString();
        final String fphonenumber = ephonenumber.getText().toString();
        String password = epassword.getText().toString();

        boolean vemail = isValidEmail(email);
        boolean vphonenumber = isValidNumber(fphonenumber);

        if(vemail&&vphonenumber)
        {
            Toast.makeText(getApplicationContext(),"Please wait...",Toast.LENGTH_LONG).show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d("createduser", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                Toast.makeText(getApplicationContext(),"Registration Success...",Toast.LENGTH_LONG).show();

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if(user!=null)
                                {
                                    String email = user.getEmail();
                                    String name = fname;
                                    String phonenumber = fphonenumber;
                                    String uid = user.getUid();

                                    myRef = myRef.child("Register").child(uid);
                                    myRef.child("email").setValue(email);
                                    myRef.child("name").setValue(name);
                                    myRef.child("phonenumber").setValue(phonenumber);
                                    myRef.child("otp").setValue("0");
                                    myRef.child("otpvalidity").setValue(System.currentTimeMillis());

                                }

                                Intent intent = new Intent(Register.this,SignIn.class);
                                startActivity(intent);
                            }

                            else {
                                Toast.makeText(getApplicationContext(),"Registration failed...",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
        else
            Toast.makeText(this,"Enter valid Data",Toast.LENGTH_LONG).show();

    }

    public  boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public  boolean isValidNumber(String target){
        boolean res = false;
        try
        {
            if(target.length()==10)
            {
                Long no = Long.parseLong(target);
                res = true;
            }
        }
        catch (Exception e){}
        return res;
    }


}
