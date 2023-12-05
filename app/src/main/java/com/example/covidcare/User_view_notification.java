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

public class User_view_notification extends Activity implements JsonResponse {
	ListView lv1;
	String [] noti_id,title,des,val,d_t;
	public static String i_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_notification);
		
		lv1=(ListView)findViewById(R.id.lvideas);
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_notification.this;
        String q = "/user_view_noti";
        q=q.replace(" ","%20");
        JR.execute(q);	
	}



	

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("user_view_noti")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				 noti_id=new String[ja1.length()];
				 title=new String[ja1.length()];
				 des=new String[ja1.length()];
				 d_t=new String[ja1.length()];

				
				 val=new String[ja1.length()];
		     
		    
			     
				for(int i = 0;i<ja1.length();i++)
				{ 
					
					noti_id[i]=ja1.getJSONObject(i).getString("notification_id");
					title[i]=ja1.getJSONObject(i).getString("title");
					des[i]=ja1.getJSONObject(i).getString("description");
					d_t[i]=ja1.getJSONObject(i).getString("date_time");
					
				
					Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
					val[i]="Title : "+title[i]+"\nDescription"+des[i]+"\nDate"+d_t[i];
					
				
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
