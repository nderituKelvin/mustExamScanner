package com.kayvoh.must;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nderitu Kelvin on 2/3/2017.
 */

public class ScanRequest extends StringRequest{
    private Map<String, String> params;

    public ScanRequest(final String url, String cipher, String unid, Response.Listener<String> listener){
        super(Method.POST, url, listener, null);
        try{
            params = new HashMap<>();
            params.put("androidStidCipher", "twende");
            params.put("cipher", cipher);
            params.put("unid", unid);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
