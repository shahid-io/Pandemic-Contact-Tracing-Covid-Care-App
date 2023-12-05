package com.example.covidcare;

import org.json.JSONObject;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class User_registration extends Activity  implements JsonResponse{
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
	Button bt1;
	RadioButton r1,r2;
	String fname,lname,gender,phone,dob,hname,place,email,uname,pass;
	DatePickerDialog datePickerDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_registration);
		e1=(EditText)findViewById(R.id.etf);
		e2=(EditText)findViewById(R.id.etl);
		e3=(EditText)findViewById(R.id.etph);
		e4=(EditText)findViewById(R.id.etem);
		e5=(EditText)findViewById(R.id.etdob);
		e6=(EditText)findViewById(R.id.eth);
		e7=(EditText)findViewById(R.id.etpl);
		e8=(EditText)findViewById(R.id.etu);
		e9=(EditText)findViewById(R.id.etpa);
		bt1=(Button)findViewById(R.id.btreg);
		
		r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);

		e5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// calender class's instance and get current date , month and year from calender
				final Calendar c = Calendar.getInstance();
				int mYear = c.get(Calendar.YEAR); // current year
				int mMonth = c.get(Calendar.MONTH); // current month
				int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
				// date picker dialog
				datePickerDialog = new DatePickerDialog(User_registration.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {
								String month = "", day_temp = "";
								// set day of month , month and year value in the edit text
								if (dayOfMonth < 10)
									day_temp = "0" + String.valueOf(dayOfMonth);
								else
									day_temp = String.valueOf(dayOfMonth);
								monthOfYear += 1;
								if (monthOfYear < 10)
									month = "0" + monthOfYear;
								else
									month = String.valueOf(monthOfYear);

								e5.setText(year + "-" + month + "-" + day_temp);

							}
						}, mYear, mMonth, mDay);
				datePickerDialog.show();
			}
		});

bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				phone=e3.getText().toString();
			    email=e4.getText().toString();
				dob=e5.getText().toString();
				hname=e6.getText().toString();
				place=e7.getText().toString();
				uname=e8.getText().toString();
				pass=e9.getText().toString();
				
				
				
				startService(new Intent(getApplicationContext(),LocationService.class));
				if(r1.isChecked())
				{
					gender="Male";
				}
				else if(r2.isChecked())
				{
					gender="Female";
				}
				
				else if(fname.equalsIgnoreCase(""))
				{
				  e1.setError("please enter your first name");
				  e1.setFocusable(true);
				}
				else if(lname.equalsIgnoreCase(""))
				{
					e2.setError("please enter your last name");
					  e2.setFocusable(true);
				}
				else if(phone.equalsIgnoreCase("")&&phone.length()!=10)
				{
					e3.setError("enter your phone no. in correct format");
					  e3.setFocusable(true);
				}
				else if(email.equalsIgnoreCase(""))
				{
					e4.setError("please enter email");
					  e4.setFocusable(true);
				}
				else if(dob.equalsIgnoreCase(""))
				{
					e5.setError("please enter your dob");
					  e5.setFocusable(true);
				}
				else if(hname.equalsIgnoreCase(""))
				{
					e6.setError("please enter your housename");
					  e6.setFocusable(true);
				}
				else if(place.equalsIgnoreCase(""))
				{
					e7.setError("please enter your place");
					  e7.setFocusable(true);
				}
				else if(uname.equalsIgnoreCase(""))
				{
					e8.setError("please enter your username");
					e8.setFocusable(true);
				}
				else if(pass.equalsIgnoreCase("")&&pass.length()!=6)
				{
					e9.setError("please enter your password..Password should be 6 characters");
					  e9.setFocusable(true);
				}
				
				JsonReq JR=new JsonReq();
		        JR.json_response=(JsonResponse) User_registration.this;
		        String q = "/userregister?fname="+fname+"&lname="+lname+"&phone="+phone+"&email="+email+"&dob="+dob+"&hname="+hname+"&gender="+gender+"&place="+place+"&uname="+uname+"&pass="+pass+"&lati="+LocationService.lati+"&longi="+LocationService.logi;
		        q=q.replace(" ","%20");
		        JR.execute(q);
				

				
			}
		});
	}



	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String status=jo.getString("status");
			Log.d("pearl",status);
			
			
			if(status.equalsIgnoreCase("success")){

				 Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
				 startActivity(new Intent(getApplicationContext(),Login.class));
				 
			}
			else if(status.equalsIgnoreCase("duplicate")){
				
				
				 startActivity(new Intent(getApplicationContext(),User_registration.class));
				 Toast.makeText(getApplicationContext(), "Username already Exist...", Toast.LENGTH_LONG).show();
				 
			}
			else
			{    				 
				startActivity(new Intent(getApplicationContext(),User_registration.class));

				Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
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
		Intent b=new Intent(getApplicationContext(),Login.class);			
		startActivity(b);
	}

}
