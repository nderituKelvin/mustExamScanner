package com.kayvoh.must;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nderitu Kelvin on 2/1/2017.
 */

public class SubmitData extends StringRequest{
    private Map<String, String> params;


    public SubmitData(String url, String stid, String unid, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        try{
            params = new HashMap<>();
            params.put("androidApproval", "twende");
            params.put("unid", unid);
            params.put("stid", stid);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}