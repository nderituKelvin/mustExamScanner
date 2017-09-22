package com.kayvoh.must;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends AppCompatActivity {
    EditText edUnit, edReg, edPass;
    Button btnLogin;
    String unitCode, regNo, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUnit = (EditText)findViewById(R.id.unitCode);
        edReg = (EditText)findViewById(R.id.lecReg);
        edPass = (EditText)findViewById(R.id.lecPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitCode = edUnit.getText().toString();
                regNo = edReg.getText().toString();
                password = edPass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String course = jsonObject.getString("course");
                                String unitCody = jsonObject.getString("unitCode");
                                String sem = jsonObject.getString("sem");
                                String unid = jsonObject.getString("unid");
                                String unitName = jsonObject.getString("unitName");
                                String lecName = jsonObject.getString("lecName");
                                String jobNo = jsonObject.getString("jobNo");
                                String usid = jsonObject.getString("usid");
                                String proffPic = jsonObject.getString("proffPic");
                                String regStudents = jsonObject.getString("regStudents");

                                // start pushing intent
                                Intent in = new Intent(MainActivity.this, HomeScreen.class);
                                in.putExtra("course", course);
                                in.putExtra("unitCody", unitCody);
                                in.putExtra("sem", sem);
                                in.putExtra("unid", unid);
                                in.putExtra("unitName", unitName);
                                in.putExtra("lecName", lecName);
                                in.putExtra("regStudents", regStudents);
                                in.putExtra("jobNo", jobNo);
                                in.putExtra("usid", usid);
                                in.putExtra("proffPic", proffPic);
                                startActivity(in);
                                finish();
                            }else{
                                Crouton.makeText(MainActivity.this, "Login Failed", Style.ALERT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                try{
                    LoginRequest registerRequest = new LoginRequest("http://"+getString(R.string.serverIP)+"/bin/kelvin.php", unitCode, regNo, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(registerRequest);
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Network Connectivity Issue", Toast.LENGTH_LONG).show();
                }
                edPass.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}