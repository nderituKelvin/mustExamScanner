package com.kayvoh.must;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nderitu Kelvin on 2/1/2017.
 */

public class LoginRequest extends StringRequest{
    private Map<String, String> params;

    public LoginRequest(final String url, String unitCode, String regNo, String password, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        try{
            params = new HashMap<>();
            params.put("androidLogin", "twende");
            params.put("unitCode", unitCode);
            params.put("reg", regNo);
            params.put("password", password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
