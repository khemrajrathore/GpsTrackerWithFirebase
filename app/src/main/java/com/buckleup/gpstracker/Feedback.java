package com.buckleup.gpstracker;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Feedback extends AppCompatActivity {
    String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

    }

    public void sendFeedback(View view)
    {

        final EditText emsg = (EditText)findViewById(R.id.feedbackmsg);
        String msg = emsg.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        emsg.setText(null);
                        showToast("Feedback Sent Successfully..Thank you");

                    }
                    else {

                        showToast("Your feedback was not recorded..Try again");

                    }

                } catch (Exception e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Feedback.this);
                    builder1.setMessage("Try again").setNegativeButton("Retry", null).create().show();
                    e.printStackTrace();

                }
            }
        };

        FeedbackRequest signinRequest = new FeedbackRequest(name,msg, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Feedback.this);
        queue.add(signinRequest);

    }

    public void showToast(String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }
}
