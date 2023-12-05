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

public class User_view_vaccine_available_hospitals extends Activity implements JsonResponse,OnItemClickListener {
    ListView lv1;
    String [] vc_id,h_id,h_name,place,landmark,phone,email,lati,longi,vc_name,val;
    public static String h_ids,vc_ids,lts,lgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_vaccine_available_hospitals);

        lv1=(ListView)findViewById(R.id.lvint);
        lv1.setOnItemClickListener(this);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_vaccine_available_hospitals.this;
        String q = "/user_view_vaccine_hospital?lati="+LocationService.lati+"&logi="+LocationService.logi;
        q=q.replace(" ","%20");
        JR.execute(q);
    }





    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("user_view_vaccine_hospital")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    vc_id=new String[ja1.length()];
                    h_id=new String[ja1.length()];
                    h_name=new String[ja1.length()];
                    place=new String[ja1.length()];
                    landmark=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    email=new String[ja1.length()];
                    lati=new String[ja1.length()];
                    longi=new String[ja1.length()];
                    vc_name=new String[ja1.length()];

                    val=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {

                        vc_id[i]=ja1.getJSONObject(i).getString("vaccine_id");
                        h_id[i]=ja1.getJSONObject(i).getString("hospital_id");
                        h_name[i]=ja1.getJSONObject(i).getString("name");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        landmark[i]=ja1.getJSONObject(i).getString("landmark");
                        phone[i]=ja1.getJSONObject(i).getString("phone");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        lati[i]=ja1.getJSONObject(i).getString("latitude");
                        longi[i]=ja1.getJSONObject(i).getString("longitude");
                        vc_name[i]=ja1.getJSONObject(i).getString("vaccine_name");


                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Hospital Name : "+h_name[i]+"\nVaccine Name:  "+vc_name[i]+"\nPlace:  "+place[i]+"\nLandmark:  "+landmark[i]+"\nPhone:  "+phone[i]+"\nEmail:  "+email[i];


                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                    lv1.setAdapter(ar);



                }

                else {
                    Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

                }
            }

            else if(method.equalsIgnoreCase("book_vaccine")){

                Toast.makeText(getApplicationContext(), "REQUEST SEND SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),User_view_vaccine_available_hospitals.class));

            }
            else if(method.equalsIgnoreCase("duplicate")){


                startActivity(new Intent(getApplicationContext(),User_view_vaccine_available_hospitals.class));
                Toast.makeText(getApplicationContext(), " Already Booked...", Toast.LENGTH_LONG).show();

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
        h_ids = h_id[arg2];
        vc_ids = vc_id[arg2];
        lts = lati[arg2];
        lgs = longi[arg2];


            final CharSequence[] items = {"View Map", "Book", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(User_view_vaccine_available_hospitals.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("View Map")) {
                        String url = "http://www.google.com/maps?saddr=" + LocationService.lati + "" + "," + LocationService.logi + "" + "&&daddr=" + User_view_vaccine_available_hospitals.lts + "," + User_view_vaccine_available_hospitals.lgs;
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(in);
                    }
                    if (items[item].equals("Book")) {

                        JsonReq JR1=new JsonReq();
                        JR1.json_response=(JsonResponse) User_view_vaccine_available_hospitals.this;
                        String q1 = "/book_vaccine?loginid="+Login.logid+"&vc_ids="+vc_ids;
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
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }



}
