package com.buckleup.gpstracker;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2/15/2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://buckleup.esy.es/myregister.php";
    private Map<String,String> params;

    public RegisterRequest(String name,String email,String phonenumber, String password, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("email",email);
        params.put("phonenumber",phonenumber);
        params.put("password",password);

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


}
