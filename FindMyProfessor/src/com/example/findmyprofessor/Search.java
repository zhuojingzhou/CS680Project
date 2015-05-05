package com.example.findmyprofessor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends Activity implements OnItemSelectedListener, OnClickListener {
	TextView department;
	TextView professor;
	String[] departs = { "Department", "CIS", "Art", "Managment"};
	String[] names ={"James Pepe","Wendy Lucas"};
	Spinner spin1;
	Spinner spin2;
	ArrayList<String> profNames;
	private DatabaseHelper dbHelper;
	private ArrayList<Professor> profList;
	private String profName;
	private Button button4;
	private Button button5;
	// Stop the spinner autoselect the first value
	private int checkSpinner = 0;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.search);
		
		dbHelper = new DatabaseHelper(this);

		button4 = (Button) findViewById(R.id.button4);
    	button4.setOnClickListener(this);
    	button5 = (Button) findViewById(R.id.button5);
    	button5.setOnClickListener(this); 
		spin1 = (Spinner) findViewById(R.id.spinner1);
		spin2 = (Spinner) findViewById(R.id.spinner2);
		spin1.setOnItemSelectedListener(this);   //set listener
		spin2.setOnItemSelectedListener(this);
		ArrayAdapter<String> aa1 = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_spinner_item,  //Android supplied Spinner item format
				departs);
		//set resource to use for drop down view, Android supplied format
		aa1.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);  
		spin1.setAdapter(aa1);  //connect ArrayAdapter to <Spinner>
		
	}
	

	//listener methods for callbacks 
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		checkSpinner++;
		if (checkSpinner > 1) {
			Spinner spinner = (Spinner) parent;
			if (spinner.getId() == R.id.spinner1) {
				Log.d("Department selected", departs[position]);
				String dpt = departs[position];
				profNames = dbHelper.getProfNamesByDpt(dpt);
				ArrayAdapter<String> aa2 = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, // Android supplied Spinner item format
						profNames);
				// set resource to use for drop down view, Android supplied
				// format
				aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spin2.setAdapter(aa2); // connect ArrayAdapter to <Spinner>
			} else if (spinner.getId() == R.id.spinner2) {
				profName = profNames.get(position);
			}
		}
		
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) { 
        case R.id.button4:
        	Intent i1 = new Intent(this, ProfInfo.class);
        	i1.putExtra("ProfessName", profName);
            startActivity(i1);
           break;
      
        case R.id.button5:
        	Intent i2 = new Intent(this, RatingForm.class);
        	i2.putExtra("ProfessName", profName);
            startActivity(i2);
           break;
       
        }
		
	}
	
}//class

