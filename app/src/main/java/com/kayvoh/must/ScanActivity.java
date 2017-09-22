package com.kayvoh.must;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONObject;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning Exam Card.......");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();


        Intent info = getIntent();
        String success = info.getStringExtra("error");
        if(success!=null){
            Toast.makeText(ScanActivity.this, success, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String info = result.getContents();
        sendToStudentDetail(info);
        //super.onActivityResult(requestCode, resultCode, data);
    }

    public void sendToStudentDetail(String cipher) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE", response);
                jsonyIn(response);
            }
        };
        SharedPreferences sharedPreferences = ScanActivity.this.getSharedPreferences("APP_PREF_FILE", MODE_PRIVATE);
        String unid = sharedPreferences.getString("unid", "false");
        ScanRequest scanRequest = new ScanRequest("http://"+getString(R.string.serverIP)+"/bin/kelvin.php", cipher, unid, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ScanActivity.this);
        queue.add(scanRequest);
    }

    public void jsonyIn(String jsony) {
        try {
            JSONObject jsonObject = new JSONObject(jsony);
            boolean success = jsonObject.getBoolean("success");
            if(success){
                String stid = jsonObject.getString("stid");
                String regNo = jsonObject.getString("regNo");
                String names = jsonObject.getString("names");
                String pic = jsonObject.getString("pic");
                int allow = jsonObject.getInt("allow");

                SharedPreferences stdsave = ScanActivity.this.getSharedPreferences("STUDENTDB", MODE_PRIVATE);
                SharedPreferences.Editor editor = stdsave.edit();
                editor.putString("stid", stid);
                editor.putString("regNo", regNo);
                editor.putString("names", names);
                editor.putString("pic", pic);
                editor.putInt("allow", allow);
                editor.apply();
                Intent kayin = new Intent(ScanActivity.this, StudentDetail.class);
                startActivity(kayin);
                finish();
            }else{
                Toast.makeText(ScanActivity.this, "Student Not Qualified To Sit Exam", Toast.LENGTH_LONG).show();
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(ScanActivity.this, "Invalid QR Code", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}