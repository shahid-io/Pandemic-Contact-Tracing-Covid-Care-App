package com.example.covidcare;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class User_view_events extends Activity implements JsonResponse {
    ListView lv1;
    String [] ev_id,worker,event,des,venue,guest,ev_date,ev_time,ev_stat,val;
    public static String i_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_events);

        lv1=(ListView)findViewById(R.id.lvideas);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_events.this;
        String q = "/user_view_events?loginid="+Login.logid;

        q=q.replace(" ","%20");
        JR.execute(q);
    }





    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_view_events")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    ev_id=new String[ja1.length()];
                    worker=new String[ja1.length()];
                    event=new String[ja1.length()];
                    venue=new String[ja1.length()];
                    des=new String[ja1.length()];
                    guest=new String[ja1.length()];
                    ev_date=new String[ja1.length()];
                    ev_time=new String[ja1.length()];
                    ev_stat=new String[ja1.length()];

                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        ev_id[i]=ja1.getJSONObject(i).getString("event_id");
                        worker[i]=ja1.getJSONObject(i).getString("w_name");
                        event[i]=ja1.getJSONObject(i).getString("event");
                        venue[i]=ja1.getJSONObject(i).getString("venue");
                        des[i]=ja1.getJSONObject(i).getString("description");
                        guest[i]=ja1.getJSONObject(i).getString("no_of_guest");
                        ev_date[i]=ja1.getJSONObject(i).getString("event_date");
                        ev_time[i]=ja1.getJSONObject(i).getString("date_time");
                        ev_stat[i]=ja1.getJSONObject(i).getString("status");


                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Worker Name : "+worker[i]+"\nEventv : "+event[i]+"\nVenue : "+venue[i]+"\nDesctiption : "+des[i]+"\nNo Of Guest : "+guest[i]+"\nDate : "+ev_date[i]+"\nTime : "+ev_time[i]+"\nStatus : "+ev_stat[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }
        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),User_manage_events.class);
        startActivity(b);
    }

}
