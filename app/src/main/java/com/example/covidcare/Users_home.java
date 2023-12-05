package com.example.covidcare;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Users_home extends Activity {
	
	Button b1,b2,b3,b4,b5,b6,b7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users_home);
		
		b1=(Button)findViewById(R.id.bthosptail);
		b2=(Button)findViewById(R.id.btnoti);
		b4=(Button)findViewById(R.id.btevent);
		b5=(Button)findViewById(R.id.btzone);
		b6=(Button)findViewById(R.id.btbooking);
		b7=(Button)findViewById(R.id.btlogout);
		
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_vaccine_available_hospitals.class));

			}
		});
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_notification.class));
			}
		});
//
//		b3.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//
//				startActivity(new Intent(getApplicationContext(),User_view_hotels.class));
//			}
//		});
//
		b4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_manage_events.class));
			}
		});

		b5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_view_nearby_containment_zone.class));
			}
		});
//
		b6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),User_book_vaccine.class));
			}
		});
		
		b7.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Login.class));
			}
		});
	}



}
