package com.kayvoh.must;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class MainList extends AppCompatActivity {
    private ListView lv;
    private KayListAdapter kayListAdapter;
    private List<KayList> kayList;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listy);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("STUDLIST", MODE_PRIVATE);
        String list = sharedPreferences.getString("theList", "No_Students");

        try{
            JSONObject jsony = new JSONObject(list);
            JSONArray jsArr = jsony.getJSONArray("stid");
            Log.d("ECHOECH0", jsArr.length()+"");
            if(jsArr.length()==0){
                Toast.makeText(getApplicationContext(), "No Student Found", Toast.LENGTH_LONG).show();
                Intent it = new Intent(getApplicationContext(), HomeScreen.class);
                startActivity(it);
                finish();
            }
            lv = (ListView)findViewById(R.id.listView);
            kayList = new ArrayList<>();

            for(int i = 0; i<jsArr.length(); ++i){
                JSONObject student = jsArr.getJSONObject(i);
                String status = student.getString("0");
                String regNo = student.getString("regNo");
                String name = student.getString("fname")+" "+student.getString("lname");
                String img = student.getString("fname")+""+student.getString("lname");
                kayList.add(new KayList(name, regNo, "http://"+getString(R.string.serverIP)+"/lecturer/proffPics/" +img, status));
            }
        }catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "An Error Occurred", Toast.LENGTH_LONG).show();
            Intent it = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(it);
            finish();
        }

        kayListAdapter = new KayListAdapter(getApplicationContext(), kayList);
        lv.setAdapter(kayListAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}