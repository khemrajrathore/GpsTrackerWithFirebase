package com.buckleup.gpstracker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 4/17/2017.
 */

public class FeedbackRequest extends StringRequest{


    private static final String LOGIN_REQUEST_URL = "http://buckleup.esy.es/SendFeedback.php";
    private Map<String,String> params;

    public FeedbackRequest(String name,String msg,Response.Listener<String> listener) {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("msg",msg);

    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
