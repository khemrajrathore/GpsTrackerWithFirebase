package com.buckleup.gpstracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendValidation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText fnumber;
    EditText fotp;
    String friendnumber;
    String friendotp;
    String lat;
    String lon;
    String phonenumber;
    String name;
    String email;
    String otp;
    String password;
    String otpvalidity1;
    private static final int REQUEST_CODE = 1;
    Long otpvalidity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_validation);




        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("phonenumber");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        otp = intent.getStringExtra("otp");
        password = intent.getStringExtra("password");
        otpvalidity1 = intent.getStringExtra("otpvalidity");

        fnumber = (EditText)findViewById(R.id.friendmobile);
        fotp = (EditText)findViewById(R.id.friendotp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void getContacts(View view)
    {
        Uri uri = Uri.parse("content://contacts");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);

                Log.d("Contacts", "ZZZ number : " + number +" , name : "+name);
                char[] ch = number.toCharArray();
                if(ch[0]=='+')
                    number = number.substring(3);
                number = number.replaceAll(" ","");
                EditText editText =(EditText)findViewById(R.id.friendmobile);
                editText.setText(number);

            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friend_validation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this,MyProfile.class);
            intent.putExtra("phonenumber", phonenumber);
            intent.putExtra("name",name);
            intent.putExtra("password", password);
            intent.putExtra("email",email);
            intent.putExtra("otp",otp);
            startActivity(intent);
        } else if (id == R.id.nav_otp) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.otpdisplay);
            dialog.setTitle("My OTP");
            TextView editText1 = (TextView) dialog.findViewById(R.id.otpdisplay_otp);
            TextView editText2 = (TextView) dialog.findViewById(R.id.otpdisplay_validity);
            if(Integer.parseInt(otp)==0)
            {
                editText1.setText("Generate Otp First");
                editText2.setText("");
            }
            else {
                editText1.setText(otp);
                editText2.setText(otpvalidity1);
            }

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        } else if (id == R.id.nav_about) {

            Intent intent = new Intent(this,AboutUs.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            if(otp.equals("0"))
            {
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.otpdisplay);
                dialog.setTitle("My OTP");
                TextView editText1 = (TextView) dialog.findViewById(R.id.otpdisplay_otp);
                TextView editText2 = (TextView) dialog.findViewById(R.id.otpdisplay_validity);
                editText1.setText("Generate Otp First");
                editText2.setText("");

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            else {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "My OTP");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hii Buddy " + otp + " is my OTP. You can easily track me using this OTP...!!! ");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        } else if (id == R.id.nav_send) {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"My App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hii Buddy want to download this app Plz refer AboutUs...!!! ");
            startActivity(Intent.createChooser(sharingIntent,"Share via"));

        } else if (id == R.id.nav_logout) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

        }
        else if(id == R.id.nav_exit)  {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Click Yes to Exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getFriendLocation(View view)
    {
        friendnumber = fnumber.getText().toString();
        friendotp = fotp.getText().toString();


        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    lat = jsonResponse.getString("latitude");
                    lon = jsonResponse.getString("longitude");
                    otpvalidity = jsonResponse.getLong("otpvalidity");
                    //String userlog = jsonResponse.getString("userlog");
                    // EditText elat = (EditText)findViewById(R.id.editText);
                    // EditText elon = (EditText)findViewById(R.id.editText2);
                    // elat.setText(lat);
                    // elon.setText(lon);
                    if(success){

                        if(otpvalidity>System.currentTimeMillis()) {
                            //Log.d("Check","Before Calling userlog "+userlog);
                            Intent intent = new Intent(FriendValidation.this, FriendMapsActivity.class);
                            intent.putExtra("latitude", lat);
                            intent.putExtra("longitude", lon);
                            //intent.putExtra("userlog",userlog+"");
                            FriendValidation.this.startActivity(intent);
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FriendValidation.this);
                            builder.setMessage("Your Friend's OTP has Expired... Request for new otp").setNegativeButton("OK",null).create().show();
                        }
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendValidation.this);
                        builder.setMessage("The Mobile Number and OTP does not match").setNegativeButton("Retry",null).create().show();
                    }


                }
                catch (JSONException e) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(FriendValidation.this);
                    builder1.setMessage("The Mobile Number and OTP does not match").setNegativeButton("Retry",null).create().show();
                    e.printStackTrace();
                }
            }
        };
        FriendLatLongRequest registerRequest = new FriendLatLongRequest(friendnumber,friendotp,responseListener);
        RequestQueue queue = Volley.newRequestQueue(FriendValidation.this);
        queue.add(registerRequest);


    }
}
