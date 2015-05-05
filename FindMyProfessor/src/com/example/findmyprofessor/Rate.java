package com.example.findmyprofessor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.*;
import android.database.sqlite.*;
import android.util.Log;

import java.util.ArrayList;

public class Rate extends Activity implements OnClickListener{
      private Button loginButton;
      private SQLiteDatabase db;
      private DatabaseHelper dbHelper;
      private ArrayList<User> uList;
      private boolean userFound = false;
      private EditText email;
      private EditText pw;
   
   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rate);

		dbHelper = new DatabaseHelper(this);
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLException e) {
			Log.d("FindMyProfessor", "Database failed");
		}
		uList = dbHelper.getUserList();

		email = (EditText) findViewById(R.id.ediName);
		pw = (EditText) findViewById(R.id.ediPw);
		// Get a handle to all user interface elements

		loginButton = (Button) findViewById(R.id.button3);

		// Set button to open browser
		loginButton.setOnClickListener(this);

	}


@Override
public void onClick(View v) {
		// TODO Auto-generated method stub
		
		for (User u : uList) {
//			Log.d("databse", u.getEmail() + " " + u.getPassword());
//			Log.d("UI", email.getText() + " " + pw.getText());
//			Log.d("the test variable", String.valueOf(u.getEmail().equals(email.getText())));
//			Log.d("the test variable", String.valueOf(u.getPassword().equals(pw.getText())));
			if (u.getEmail().equals(email.getText().toString())
					&& u.getPassword().equals(pw.getText().toString())) {
				userFound = true;
				Toast.makeText(getApplicationContext(), "Welcome: " + u.getEmail(),
						Toast.LENGTH_LONG).show();
				break;
			}
		}
		
//		Log.d("the test variable", String.valueOf(userFound));
		
		if (userFound) {
			Intent i3 = new Intent(this, Search.class);
			startActivity(i3);
		} else {
			Toast.makeText(getApplicationContext(), "User doesn't exist",
					Toast.LENGTH_LONG).show();
		}
}

}

