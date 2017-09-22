package com.kayvoh.must;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nderitu Kelvin on 2/3/2017.
 */

public class ListFetch extends StringRequest{
    private Map<String, String> params;

    public ListFetch(final String url, String unid, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        try{
            params = new HashMap<>();
            params.put("getExamList", "twende");
            params.put("unid", unid+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
