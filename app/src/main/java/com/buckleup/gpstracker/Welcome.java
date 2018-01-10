package com.buckleup.gpstracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Welcome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String phonenumber;
    String name;
    String email;
    static String otp;
    String password;
    String uid;
    public static int dday = 0;
    public static int dmonth = 0;
    public static int dyear = 0;
    public static int thour = 0;
    public static int tmin = 0;
    static String otpvalidity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = getIntent();


        phonenumber = intent.getStringExtra("phonenumber");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        otp = intent.getStringExtra("otp");
        password = intent.getStringExtra("password");
        uid = intent.getStringExtra("uid");


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



    public void showMyLocation(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("phonenumber", phonenumber);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
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
                                //stopBackgroundService();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
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
                editText2.setText(otpvalidity);
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


        } else if (id == R.id.nav_exit) {

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
        }
        else if(id == R.id.nav_logout){
            finish();
            //System.exit(0);
        }
        else if (id == R.id.nav_send) {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"My App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hii Buddy want to download this app Plz refer AboutUs...!!! ");
            startActivity(Intent.createChooser(sharingIntent,"Share via"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void otpGenerator(View view)
    {


        Intent intent = new Intent(this,OtpDatePicker.class);
        intent.putExtra("phonenumber",phonenumber);
        intent.putExtra("uid",uid);
        startActivity(intent);



        /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Do you want to generate OTP?");
        alertDialogBuilder
                .setMessage("Click Yes to Generate!!!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                otpConfirmation();
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();*/
    }


    public void showFriendLocation(View view)
    {
        Intent intent = new Intent(this,FriendValidation.class);
        intent.putExtra("otp",otp);
        intent.putExtra("phonenumber", phonenumber);
        intent.putExtra("name",name);
        intent.putExtra("password", password);
        intent.putExtra("email",email);
        intent.putExtra("otp",otp);
        intent.putExtra("otpvalidity",otpvalidity);
        startActivity(intent);
    }

    public void feedback(View view)
    {
        Intent intent = new Intent(this,Feedback.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }
}
