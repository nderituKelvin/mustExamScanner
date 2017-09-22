package com.kayvoh.must;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

public class StudentDetail extends AppCompatActivity{
    TextView stdRegNo, stdName, stdStatus;
    ImageView picView;
    Button stdAcceptButton, stdHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        stdRegNo = (TextView)findViewById(R.id.stdRegNo);
        stdName = (TextView)findViewById(R.id.stdName);
        stdStatus = (TextView)findViewById(R.id.stdStatus);
        picView = (ImageView)findViewById(R.id.pic);
        stdAcceptButton = (Button)findViewById(R.id.stdAcceptButton);
        stdHomeButton = (Button)findViewById(R.id.stdHomeButton);

        //getData from STUDENTDB
        SharedPreferences stdDet = StudentDetail.this.getSharedPreferences("STUDENTDB", MODE_PRIVATE);
        final String stid = stdDet.getString("stid", "0");
        String regNo = stdDet.getString("regNo", "false");
        String names = stdDet.getString("names", "false");
        String pic = stdDet.getString("pic", "false");
        int allow = stdDet.getInt("allow", 0);

        //get the last data from Lec DB (UNIT)
        SharedPreferences unitDet = StudentDetail.this.getSharedPreferences("APP_PREF_FILE", MODE_PRIVATE);
        final String unid = unitDet.getString("unid", "false");

        stdRegNo.setText(regNo);
        stdName.setText(names);
        if(allow == 0){
            stdStatus.setText("Student Not Allowed To Sit this Examination");
            stdAcceptButton.setEnabled(false);
        }else{
            stdStatus.setText("Student Approved!!");
            stdAcceptButton.setEnabled(true);
        }

        String picUrl = "http://"+getString(R.string.serverIP)+"/meruPortal/student/proffPics/" +pic;
        Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();

        picasso.with(getApplicationContext())
                .load(picUrl)
                .transform(new RoundedTransformation(40, 0))
                .fit()
                .into(picView);
        stdAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Response", response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent in = new Intent(StudentDetail.this, HomeScreen.class);
                                in.putExtra("error", "SuccessFully Added to ExamList");
                                startActivity(in);
                                finish();
                            }else{
                                Toast.makeText(StudentDetail.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(StudentDetail.this, "Couldn't Connect to Server", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                try{
                    SubmitData submitData = new SubmitData("http://"+getString(R.string.serverIP)+"/bin/kelvin.php", stid, unid, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(StudentDetail.this);
                    queue.add(submitData);
                }catch (Exception e){
                    Toast.makeText(StudentDetail.this, "Network Connectivity Issue", Toast.LENGTH_LONG).show();
                }
            }
        });

        stdHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}