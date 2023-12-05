package com.example.covidcare;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Worker_view_events extends Activity implements JsonResponse,OnItemClickListener {
    ListView lv1;
    String [] ev_id,user,event,des,venue,guest,ev_date,ev_time,ev_stat,val;
    public static String ev_ids,ev_statuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_view_events);

        lv1=(ListView)findViewById(R.id.lvevents);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Worker_view_events.this;
        String q = "/worker_view_events?loginid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }





    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("worker_view_events")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    ev_id=new String[ja1.length()];
                    user=new String[ja1.length()];
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
                        user[i]=ja1.getJSONObject(i).getString("user_name");
                        event[i]=ja1.getJSONObject(i).getString("event");
                        venue[i]=ja1.getJSONObject(i).getString("venue");
                        des[i]=ja1.getJSONObject(i).getString("description");
                        guest[i]=ja1.getJSONObject(i).getString("no_of_guest");
                        ev_date[i]=ja1.getJSONObject(i).getString("event_date");
                        ev_time[i]=ja1.getJSONObject(i).getString("date_time");
                        ev_stat[i]=ja1.getJSONObject(i).getString("status");


                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="User Name : "+user[i]+"\nEventv : "+event[i]+"\nVenue : "+venue[i]+"\nDesctiption : "+des[i]+"\nNo Of Guest : "+guest[i]+"\nDate : "+ev_date[i]+"\nTime : "+ev_time[i]+"\nStatus : "+ev_stat[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

            else if(method.equalsIgnoreCase("worker_update_event_status")){

                Toast.makeText(getApplicationContext(), "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Worker_view_events.class));

            }



        }catch (Exception e)
        {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }



    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        ev_ids = ev_id[arg2];
        ev_statuss = ev_stat[arg2];


        if (ev_statuss.equalsIgnoreCase("Upcomming")) {


            final CharSequence[] items = {"Accept", "Reject", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Worker_view_events.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Accept")) {

                        JsonReq JR1 = new JsonReq();
                        JR1.json_response = (JsonResponse) Worker_view_events.this;
                        String q1 = "/worker_update_event_status?action=accept&loginid=" + Login.logid + "&ev_ids=" + ev_ids;
                        q1 = q1.replace(" ", "%20");
                        JR1.execute(q1);

                    }
                    if (items[item].equals("Reject")) {

                        JsonReq JR1 = new JsonReq();
                        JR1.json_response = (JsonResponse) Worker_view_events.this;
                        String q1 = "/worker_update_event_status?action=reject&loginid=" + Login.logid + "&ev_ids=" + ev_ids;
                        q1 = q1.replace(" ", "%20");
                        JR1.execute(q1);

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(i, GALLERY_CODE);
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
