package com.buckleup.gpstracker;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2/15/2017.
 */

public class LatLonToDB extends StringRequest {

    private static final String LATLON_REQUEST_URL = "http://buckleup.esy.es/latlongupdate.php";
    private Map<String,String> params;

    public LatLonToDB(String phonenumber,String latitude,String longitude,String userlog, Response.Listener<String> listener)
    {
        super(Method.POST,LATLON_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("phonenumber",phonenumber);
        params.put("latitude",latitude);
        params.put("longitude",longitude);
        params.put("userlog",userlog);
        Log.d("DATE",userlog);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


}
