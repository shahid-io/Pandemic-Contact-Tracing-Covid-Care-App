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

public class Worker_duties extends Activity implements JsonResponse,OnItemClickListener {
    ListView lv1;
    String [] dt_id,title,des,date_time,dt_status,val;
    public static String dt_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_duties);

        lv1=(ListView)findViewById(R.id.lvdty);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Worker_duties.this;
        String q = "/worker_view_duties?loginid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
    }



    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("worker_view_duties")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    dt_id=new String[ja1.length()];
                    title=new String[ja1.length()];
                    des=new String[ja1.length()];
                    date_time=new String[ja1.length()];
                    dt_status=new String[ja1.length()];
                    val=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        dt_id[i]=ja1.getJSONObject(i).getString("duty_id");
                        title[i]=ja1.getJSONObject(i).getString("title");
                        des[i]=ja1.getJSONObject(i).getString("description");
                        date_time[i]=ja1.getJSONObject(i).getString("date_time");
                        dt_status[i]=ja1.getJSONObject(i).getString("status");

                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Title : "+title[i]+"\nDescription :  "+des[i]+"\nDate:  "+date_time[i]+"\nStatus:  "+dt_status[i];



                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

            else if(method.equalsIgnoreCase("worker_update_status")){

                Toast.makeText(getApplicationContext(), "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Worker_duties.class));

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

        dt_ids= dt_id[arg2];


        final CharSequence[] items = {"Update Status", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Worker_duties.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Update Status")) {

                    JsonReq JR1=new JsonReq();
                    JR1.json_response=(JsonResponse) Worker_duties.this;
                    String q1 = "/worker_update_status?loginid="+Login.logid+"&dt_ids="+dt_ids;
                    q1=q1.replace(" ","%20");
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

    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Worker_home.class);
        startActivity(b);
    }



}
