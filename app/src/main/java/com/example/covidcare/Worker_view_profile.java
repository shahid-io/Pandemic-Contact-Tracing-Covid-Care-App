package com.example.covidcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Worker_view_profile extends AppCompatActivity implements JsonResponse {

    EditText e1,e2,e3,e4,e5,e6;
    String fname,lname,house,place,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_view_profile);

        e1=(EditText)findViewById(R.id.etfirstname);
        e2=(EditText)findViewById(R.id.etlastname);
        e3=(EditText)findViewById(R.id.ethouse);
        e4=(EditText)findViewById(R.id.etplace);
        e5=(EditText)findViewById(R.id.etPhone);
        e6=(EditText)findViewById(R.id.etemail);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Worker_view_profile.this;
        String q = "/wviewprofile/?logid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status=jo.getString("status");
            Log.d("pearl",status);
            //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();

            if(status.equalsIgnoreCase("success")){
                JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                e1.setText(ja1.getJSONObject(0).getString("first_name"));
                e2.setText(ja1.getJSONObject(0).getString("last_name"));
                e3.setText(ja1.getJSONObject(0).getString("house_name"));
                e4.setText(ja1.getJSONObject(0).getString("place"));
                e5.setText(ja1.getJSONObject(0).getString("phone"));
                e6.setText(ja1.getJSONObject(0).getString("email"));

//



            }
            else {
                Toast.makeText(getApplicationContext(),"NoData ",Toast.LENGTH_LONG).show();
//				Intent i=new Intent(getApplicationContext(),MainLogin.class);
//                startActivity(new Intent(getApplicationContext(),Login.class));
            }


        }catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Worker_home.class);
        startActivity(b);
    }
}