package com.buckleup.gpstracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FriendMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    ZoomControls zoom;
    Button markBt;
    Button geoLocationBt;
    Button satView;
    Button clear;
    Double myLatitude = null;
    Double myLongitude = null;
    Double fLatitude = null;
    Double fLongitude = null;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    protected static final String TAG = "MapsActvity";
    //String userlog;
    //String dateandtime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //userlog = intent.getStringExtra("userlog");
        //Log.d("Check","After Calling userlog "+userlog);
        try {
            fLatitude = Double.parseDouble(intent.getStringExtra("latitude"));
            fLongitude = Double.parseDouble(intent.getStringExtra("longitude"));
        }
        catch(Exception e){}
        setContentView(R.layout.activity_friend_maps);

        /*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        long milliSeconds= Long.parseLong(userlog);
        Log.d("Milliseconds",milliSeconds+"");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Log.d("Time",formatter.format(calendar.getTime()));
        dateandtime = formatter.format(calendar.getTime());*/

        //myLatitude = Double.parseDouble(LocationService.finlat);
        //myLongitude = Double.parseDouble(LocationService.finlon);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = new LocationRequest();
        locationRequest.setInterval(15 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //set to balanced power accuracy on real device

        zoom = (ZoomControls) findViewById(R.id.zcZoom);
        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());

            }
        });
        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());

            }
        });

        markBt = (Button) findViewById(R.id.btMark);
        markBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng myLocation = new LatLng(myLatitude, myLongitude);
                mMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
            }
        });

        geoLocationBt = (Button) findViewById(R.id.btSearch);
        geoLocationBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchText = (EditText) findViewById(R.id.etLocationEntry);
                String search = searchText.getText().toString();
                if (search != null && !search.equals("")) {
                    List<android.location.Address> addressList = null;
                    Geocoder geocoder = new Geocoder(FriendMapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(search, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)+""));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });

        satView = (Button) findViewById(R.id.btSatellite);
        satView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    satView.setText("NORM");
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    satView.setText("SAT");
                }

            }
        });

        clear = (Button) findViewById(R.id.btClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Toast.makeText(this,"This Location was recorded at "+dateandtime,Toast.LENGTH_LONG);
        // Add a marker in Sydney and move the camera
        LatLng floc = new LatLng(fLatitude, fLongitude);
        mMap.addMarker(new MarkerOptions().position(floc).title("Friend Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(floc));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("My Click"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection Failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            requestLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
}
