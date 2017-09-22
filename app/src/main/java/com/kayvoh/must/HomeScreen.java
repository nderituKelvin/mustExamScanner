package com.kayvoh.must;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class HomeScreen extends AppCompatActivity {
    TextView homeUnitCode, homeUnitName, homeCourse, homeSem, homeLecName, homeLecRegNo, homeRegs;
    ImageView proffPicha;
    Button scan, exitApp;
    private static final String TAG = HomeScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //get the results from intent
        Intent info = getIntent();
        String course = info.getStringExtra("course");
        String unitCody = info.getStringExtra("unitCody");
        String sem = info.getStringExtra("sem");
        String unid = info.getStringExtra("unid");
        String unitName = info.getStringExtra("unitName");
        String lecName = info.getStringExtra("lecName");
        String regStudents = info.getStringExtra("regStudents");
        String jobNo = info.getStringExtra("jobNo");
        String usid = info.getStringExtra("usid");
        String proffPic = info.getStringExtra("proffPic");
        String succ = info.getStringExtra("error");
        if(succ!=null){
            Crouton.makeText(HomeScreen.this, succ, Style.INFO).show();
        }

        //get the names of the textviews
        homeUnitCode = (TextView)findViewById(R.id.homeUnitCode);
        homeUnitName = (TextView)findViewById(R.id.homeUnitName);
        homeCourse = (TextView)findViewById(R.id.homeCourse);
        homeSem = (TextView)findViewById(R.id.homeSem);
        homeLecName = (TextView)findViewById(R.id.homeLecName);
        homeLecRegNo = (TextView)findViewById(R.id.homeLecRegNo);
        homeRegs = (TextView)findViewById(R.id.homeRegs);
        exitApp = (Button)findViewById(R.id.exitApp);

        homeRegs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToScanned = new Intent(getApplicationContext(), MainList.class);
                startActivity(goToScanned);
            }
        });

        if(unitCody==null) {
            SharedPreferences shrd = HomeScreen.this.getSharedPreferences("APP_PREF_FILE", MODE_PRIVATE);
            unid = shrd.getString("unid", "false");
            course = shrd.getString("course", "false");
            unitCody = shrd.getString("unitCody", "false");
            sem = shrd.getString("sem", "false");
            unitName = shrd.getString("unitName", "false");
            lecName = shrd.getString("lecName", "false");
            regStudents = shrd.getString("regStudents", "false");
            jobNo = shrd.getString("jobNo", "false");
            usid = shrd.getString("usid", "false");
            proffPic = shrd.getString("proffpic", "false");
        }

        //set the text thingies
        homeCourse.setText("Course: " +course);
        homeUnitName.setText("Unit Name: " +unitName);
        homeUnitCode.setText(unitCody);
        homeSem.setText(sem);
        homeLecName.setText("Lec Name: " +lecName);
        homeLecRegNo.setText("Job Number: " +jobNo);
        homeRegs.setText(regStudents+ " Registered Students");

        //get and load the proffpic
        String proffPhoto = proffPic;
        String url = "http://"+getString(R.string.serverIP)+"/meruPortal/lecturer/proffPics/" +proffPhoto;
        proffPicha = (ImageView)findViewById(R.id.proffPic);
        Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        }).build();

        picasso.with(getApplicationContext())
                .load(url)
                .transform(new RoundedTransformation(20, 0))
                .fit()
                .into(proffPicha);

        //Store the details into Shared Preferences for future reference
        SharedPreferences sharedPreferences = HomeScreen.this.getSharedPreferences("APP_PREF_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course", course);
        editor.putString("unitCody", unitCody);
        editor.putString("sem", sem);
        editor.putString("unid", unid);
        editor.putString("unitName", unitName);
        editor.putString("lecName", lecName);
        editor.putString("regStudents", regStudents);
        editor.putString("jobNo", jobNo);
        editor.putString("usid", usid);
        editor.putString("proffpic", proffPic);
        editor.commit();

        scan = (Button)findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this, ScanActivity.class);
                startActivity(i);
            }
        });

        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(HomeScreen.this, MainActivity.class);
                startActivity(toLogin);
                finishAffinity();
            }
        });

        getStudentsList();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudentsList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getStudentsList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getStudentsList();
    }

    public void getStudentsList(){
        Response.Listener<String> respo = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences spStudList = HomeScreen.this.getSharedPreferences("STUDLIST", MODE_PRIVATE);
                SharedPreferences.Editor writer = spStudList.edit();
                writer.putString("theList", response);
                writer.commit();
            }
        };

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("APP_PREF_FILE", MODE_PRIVATE);
        String unid = sharedPreferences.getString("unid", "false");
        ListFetch listFetch = new ListFetch("http://"+getString(R.string.serverIP)+"/meruPortal/bin/kelvin.php", unid, respo);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(listFetch);

        SharedPreferences shrd = getApplicationContext().getSharedPreferences("STUDLIST", MODE_PRIVATE);
        String list = shrd.getString("theList", "No_Students");
    }
}