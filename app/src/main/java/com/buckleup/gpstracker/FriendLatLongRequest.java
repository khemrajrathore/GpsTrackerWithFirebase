package com.buckleup.gpstracker;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2/15/2017.
 */

public class FriendLatLongRequest extends StringRequest {

    private static final String FRIENDLATLONG_REQUEST_URL = "http://buckleup.esy.es/retrievelatlong.php";
    private Map<String,String> params;

    public FriendLatLongRequest(String phonenumber, String otp, Response.Listener<String> listener)
    {
        super(Method.POST,FRIENDLATLONG_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("phonenumber",phonenumber);
        params.put("otp",otp);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


}
