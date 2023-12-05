package com.example.covidcare;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;


import android.opengl.EGLExt;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class User_manage_events extends Activity  implements JsonResponse, OnItemSelectedListener{
    EditText e1,e2,e3,e4,e5,e8,e9;
    Button bt1,bt2;
    Spinner s1;
    String event,des,venue,guest,dt,tm,st;
    public static String worker_id;
    String[] wid,workers,val;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage_events);

        s1=(Spinner)findViewById(R.id.spworker);
        s1.setOnItemSelectedListener(this);


        e1=(EditText)findViewById(R.id.etevent);
        e2=(EditText)findViewById(R.id.etdes);
        e3=(EditText)findViewById(R.id.etvenue);
        e4=(EditText)findViewById(R.id.etguest);
        e5=(EditText)findViewById(R.id.etdate);
        e8=(EditText)findViewById(R.id.ettime);

        bt1=(Button)findViewById(R.id.btevent);
        bt2=(Button)findViewById(R.id.btv_event);

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),User_view_events.class));

            }
        });

        e5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(User_manage_events.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                e5.setText(year + "-"
                                        + (monthOfYear + 1) + "-" +dayOfMonth );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_manage_events.this;
        String q1 = "/view_worker";
        q1=q1.replace(" ","%20");
        JR1.execute(q1);




        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                event=e1.getText().toString();
                des=e2.getText().toString();
                venue=e3.getText().toString();
                guest=e4.getText().toString();
                dt=e5.getText().toString();
                tm=e8.getText().toString();



                 if(event.equalsIgnoreCase(""))
                {
                    e1.setError("please enter your Event name");
                    e1.setFocusable(true);
                }
                else if(des.equalsIgnoreCase(""))
                {
                    e2.setError("please enter your Description");
                    e2.setFocusable(true);
                }
                else if(venue.equalsIgnoreCase(""))
                {
                    e3.setError("enter your Event Venue");
                    e3.setFocusable(true);
                }
                else if(guest.equalsIgnoreCase(""))
                {
                    e4.setError("please enter No.Of Guests");
                    e4.setFocusable(true);
                }
                else if(dt.equalsIgnoreCase(""))
                {
                    e5.setError("please enter Date");
                    e5.setFocusable(true);
                }

                else if(tm.equalsIgnoreCase(""))
                {
                    e8.setError("please enter time");
                    e8.setFocusable(true);
                }

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) User_manage_events.this;
                String q = "/user_add_events?worker_id="+worker_id+"&event="+event+"&des="+des+"&venue="+venue+"&guest="+guest+"&dt="+dt+"&tm="+tm+"&loginid="+Login.logid;
                q=q.replace(" ","%20");
                JR.execute(q);



            }
        });
    }



    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub


        try {
            String method=jo.getString("method");
            if(method.equalsIgnoreCase("view_worker"))
            {
                String status=jo.getString("status");
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");

                    wid=new String[ja1.length()];
                    workers=new String[ja1.length()];


                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {
                        wid[i]=ja1.getJSONObject(i).getString("worker_id");

                        workers[i]=ja1.getJSONObject(i).getString("worker_name");

                        val[i]="Worker Name  :  "+workers[i];
                    }

                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,val);
                    s1.setAdapter(ar);

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();
                }

            }
            else if(method.equalsIgnoreCase("user_add_events")){





                Toast.makeText(getApplicationContext(), "EVENT ADDED SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),User_manage_events.class));

            }




        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }



    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        // TODO Auto-generated method stub

            worker_id=wid[arg2];


    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
