package com.buckleup.gpstracker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 4/16/2017.
 */

public class ForgetPasswordRequest extends StringRequest{

    private static final String LOGIN_REQUEST_URL = "http://buckleup.esy.es/MyForgetPassword.php";
    private Map<String,String> params;

    public ForgetPasswordRequest(String phonenumber,Response.Listener<String> listener) {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("phonenumber",phonenumber);

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
