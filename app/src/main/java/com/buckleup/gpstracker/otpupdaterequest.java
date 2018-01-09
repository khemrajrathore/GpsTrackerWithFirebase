package com.buckleup.gpstracker;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2/15/2017.
 */

public class otpupdaterequest extends StringRequest {

    private static final String OTP_REQUEST_URL = "http://buckleup.esy.es/otpupdate.php";
    private Map<String,String> params;

    public otpupdaterequest(String phonenumber,String otp,String otpvalidity, Response.Listener<String> listener)
    {
        super(Method.POST,OTP_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("phonenumber",phonenumber);
        params.put("otp",otp);
        params.put("otpvalidity",otpvalidity);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


}
