package com.kayvoh.must;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nderitu Kelvin on 2/10/2017.
 */

public class ExamListCheck extends StringRequest {
    private Map<String, String> params;
    public ExamListCheck (final String url, int unid, int stid, Response.Listener<String> skiza){
        super(Method.POST, url, skiza, null);
        try{
            params = new HashMap<>();
            params.put("getExamList", "twende");
            params.put("unid", unid+"");
            params.put("stid", stid+"");
        }catch(Exception e){

        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
