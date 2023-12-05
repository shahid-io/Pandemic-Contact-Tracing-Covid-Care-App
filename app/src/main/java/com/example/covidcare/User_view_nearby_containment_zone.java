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

public class User_view_nearby_containment_zone extends Activity implements JsonResponse,OnItemClickListener {
    ListView lv1;
    String [] zone_id,place,worker,phone,duty,des,lt,lg,val;
    public static String zn_id,lts,lgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_nearby_containment_zone);

        lv1=(ListView)findViewById(R.id.lvnear);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_nearby_containment_zone.this;
        String q = "/user_nearby_zone?lati="+LocationService.lati+"&logi="+LocationService.logi;
        q=q.replace(" ","%20");
        JR.execute(q);
    }




    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_nearby_zone")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    zone_id=new String[ja1.length()];
                    place=new String[ja1.length()];
                    worker=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    duty=new String[ja1.length()];
                    des=new String[ja1.length()];
                    lt=new String[ja1.length()];
                    lg=new String[ja1.length()];

                    val=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        zone_id[i]=ja1.getJSONObject(i).getString("zone_id");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        worker[i]=ja1.getJSONObject(i).getString("worker_name");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        duty[i]=ja1.getJSONObject(i).getString("title");
                        des[i]=ja1.getJSONObject(i).getString("description");
                        lt[i]=ja1.getJSONObject(i).getString("latitude");
                        lg[i]=ja1.getJSONObject(i).getString("longitude");

                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="place : "+place[i]+"\nWorker Name : "+worker[i]+"\nPhone : "+phone[i]+"\nDuty : "+duty[i]+"\nDescription : "+des[i];


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

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        zn_id=zone_id[arg2];
        lts=lt[arg2];
        lgs=lg[arg2];

        final CharSequence[] items = {"View Map","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_nearby_containment_zone.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Map"))
                {
                    String url = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+User_view_nearby_containment_zone.lts+","+User_view_nearby_containment_zone.lgs;
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
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
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }




}
