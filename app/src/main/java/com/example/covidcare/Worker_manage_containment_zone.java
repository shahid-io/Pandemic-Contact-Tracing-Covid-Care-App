package com.example.covidcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Worker_manage_containment_zone extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    Button b1;
    EditText e1;
    ListView l1;
    String[] zone_id,place,date,stat,value;
    public static String zid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_manage_containment_zone);
//        startService(new Intent(getApplicationContext(),LocationService.class));

        e1=(EditText)findViewById(R.id.etplace);
        b1=(Button)findViewById(R.id.btzone);

        l1=(ListView)findViewById(R.id.lvzone);
        l1.setOnItemClickListener(this);

        e1.setText(LocationService.place);
        Toast.makeText(getApplicationContext(),LocationService.place, Toast.LENGTH_LONG).show();

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) Worker_manage_containment_zone.this;
        String q1 = "/worker_view_zone?loginid="+Login.logid;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Hellooooooooo", Toast.LENGTH_LONG).show();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) Worker_manage_containment_zone.this;
                String q = "/worker_add_zone?place="+LocationService.place+"&lati="+LocationService.lati+"&longi="+LocationService.logi+"&loginid="+Login.logid;
                q=q.replace(" ","%20");
                JR.execute(q);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String method=jo.getString("method");
            if(method.equalsIgnoreCase("worker_add_zone")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " ADD", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Worker_manage_containment_zone.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Worker_manage_containment_zone.class));
                }
            }
            if(method.equalsIgnoreCase("worker_remove_zone")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                //Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), " Removed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Worker_manage_containment_zone.class));
                }
            }
            if(method.equalsIgnoreCase("worker_view_zone")){
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()];
                    zone_id=new String[ja1.length()];
                    place=new String[ja1.length()];
                    date=new String[ja1.length()];
                    stat=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        zone_id[i]=ja1.getJSONObject(i).getString("zone_id");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        date[i]=ja1.getJSONObject(i).getString("date_time");
                        stat[i]=ja1.getJSONObject(i).getString("status");
                        value[i]="Place Name:  "+place[i]+"\nDate:  "+date[i]+"\nStatus:  "+stat[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);
                    //startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));
                }

                else

                {
                    Toast.makeText(getApplicationContext(), "No Zone Details!!", Toast.LENGTH_LONG).show();

                }
            }

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }


    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        zid=zone_id[arg2];

        final CharSequence[] items = {"Remove","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Worker_manage_containment_zone.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Remove"))
                {
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse) Worker_manage_containment_zone.this;
                    String q = "/worker_remove_zone?zid="+zid;
                    q=q.replace(" ","%20");
                    JR.execute(q);
                }

                else if (items[item].equals("Cancel")) {
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
