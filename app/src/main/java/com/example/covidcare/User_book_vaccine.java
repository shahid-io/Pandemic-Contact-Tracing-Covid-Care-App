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

public class User_book_vaccine extends Activity implements JsonResponse {
    ListView lv1;
    String [] bk_id,hospital,vaccine,place,landmark,phone,email,bk_status,val;
    public static String i_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_vaccine);

        lv1=(ListView)findViewById(R.id.lvideas);

        JsonReq JR1=new JsonReq();
        JR1.json_response=(JsonResponse) User_book_vaccine.this;
        String q1 = "/view_book_vaccine?loginid="+Login.logid;
        q1=q1.replace(" ","%20");
        JR1.execute(q1);
    }


    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("view_book_vaccine")){
                String status=jo.getString("status");
                Log.d("pearl",status);
                Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
                if(status.equalsIgnoreCase("success")){

                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    bk_id=new String[ja1.length()];
                    hospital=new String[ja1.length()];
                    vaccine=new String[ja1.length()];
                    place=new String[ja1.length()];
                    landmark=new String[ja1.length()];
                    phone=new String[ja1.length()];
                    email=new String[ja1.length()];
                    bk_status=new String[ja1.length()];


                    val=new String[ja1.length()];



                    for(int i = 0;i<ja1.length();i++)
                    {

                        bk_id[i]=ja1.getJSONObject(i).getString("booking_id");
                        hospital[i]=ja1.getJSONObject(i).getString("name");
                        vaccine[i]=ja1.getJSONObject(i).getString("vaccine_name");
                        place[i]=ja1.getJSONObject(i).getString("hplace");
                        landmark[i]=ja1.getJSONObject(i).getString("landmark");
                        phone[i]=ja1.getJSONObject(i).getString("hphone");
                        email[i]=ja1.getJSONObject(i).getString("hemail");
                        bk_status[i]=ja1.getJSONObject(i).getString("status");


                        Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
                        val[i]="Hostpital : "+hospital[i]+"\nVaccine : "+vaccine[i]+"\nPlace : "+place[i]+"\nLandmark : "+landmark[i]+"\nPhone : "+phone[i]+"\nEmail : "+email[i]+phone[i]+"\nStatus : "+bk_status[i];


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
        Intent b=new Intent(getApplicationContext(),Users_home.class);
        startActivity(b);
    }

}
