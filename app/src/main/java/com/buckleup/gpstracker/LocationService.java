package com.buckleup.gpstracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Admin on 4/18/2017.
 */

public class LocationService extends Service {
    static boolean isconnected = false;
    static String finlat;
    static String finlon;
    public String phonenumber = null;

    //public String password = null;

    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;

    //Intent intent;
    int counter = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        phonenumber = intent.getStringExtra("phonenumber");
        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();
        while(true) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                break;
            }
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);*/

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        //intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId) throws SecurityException
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }



    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }



    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.d("STOP_SERVICE", "DONE");
        locationManager.removeUpdates(listener);
    }

   /* public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }*/




    public class MyLocationListener implements LocationListener
    {

        public void onLocationChanged( final Location loc)
        {
            Log.i("********", "Location changed");
            Log.i("lat ",""+loc.getLatitude());
            Log.i("lon ",""+loc.getLongitude());
            Log.i("phonenumber",phonenumber);
            isconnected = true;
            //Log.i("password",password);
            String userlog;
            String latitude = ""+loc.getLatitude();
            String longitude = ""+loc.getLongitude();
            finlat = latitude;
            finlon = longitude;
            userlog = System.currentTimeMillis()+"";

            /*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            long milliSeconds= Long.parseLong(userlog);
            Log.d("Milliseconds",milliSeconds+"");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            Log.d("Time",formatter.format(calendar.getTime()));
            userlog = formatter.format(calendar.getTime());*/
            Log.d("UserLog",userlog);



            if(isBetterLocation(loc, previousBestLocation)) {

                loc.getLatitude();
                loc.getLongitude();

                /*intent.putExtra("Latitude", loc.getLatitude());
                intent.putExtra("Longitude", loc.getLongitude());
                intent.putExtra("Provider", loc.getProvider());
                sendBroadcast(intent);*/

            }

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){
                            //AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                            //builder.setMessage("Your Location is been Updated").setPositiveButton("OK",null).create().show();
                        }
                        else
                        {
                            //AlertDialog.Builder builder = new AlertDialog.Builder(LocationService.this);
                            //builder.setMessage("Plz On Check your Internet and GPS settings").setNegativeButton("Retry",null).create().show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };


            LatLonToDB registerRequest = new LatLonToDB(phonenumber,latitude,longitude,userlog,responseListener);
            RequestQueue queue = Volley.newRequestQueue(LocationService.this);
            queue.add(registerRequest);




        }

        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }


        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }


        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }
}