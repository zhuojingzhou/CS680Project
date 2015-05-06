package com.example.findmyprofessor;

import android.util.Log;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class RatingForm extends Activity implements OnClickListener{
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	private Button button;
	private TextView professorNameText;
	private EditText assignmentTextBox;
    private EditText knowledgeTextBox;
    private EditText accessibleTextBox;
    private EditText comments1;
    private EditText userEmail;
    private String profName;
    int assignmentScore;
    int knowledgeScore;
    int accessibleScore;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.prof_rate_page);
		
		dbHelper = new DatabaseHelper(this);
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLException e) {
			Log.d("FindMyProfessor", "Database failed");
		}
		
		assignmentTextBox = (EditText)findViewById(R.id.txtAssignments);
		knowledgeTextBox = (EditText)findViewById(R.id.txtKnowledge);
		accessibleTextBox = (EditText)findViewById(R.id.txtAccessible);
		userEmail = (EditText)findViewById(R.id.txtEmailInput);
		comments1 = (EditText)findViewById(R.id.txtComment1);
		professorNameText = (TextView)findViewById(R.id.txtProfNameRate);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			profName = extras.getString("ProfessName");
		}
		
		
		button = (Button) findViewById(R.id.btnSubmit);
    	button.setOnClickListener(this);
    	professorNameText.setText(profName);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) { 
        case R.id.btnSubmit:
        	ProfessRating pr = new ProfessRating();
        	pr.setProfName(profName);
        	pr.setUserEmail(userEmail.getText().toString());
        	pr.setComment(comments1.getText().toString());
        	pr.setAssignments(Integer.valueOf(assignmentTextBox.getText().toString()));
        	pr.setKnowledge(Integer.valueOf(knowledgeTextBox.getText().toString()));
        	pr.setAccessible(Integer.valueOf(accessibleTextBox.getText().toString()));
        	dbHelper.addProfessorRating(pr);
        	Toast.makeText(getApplicationContext(), "Your submission is saved under: " + userEmail.getText().toString(),
					Toast.LENGTH_LONG).show();
        	Intent i1 = new Intent(this, Search.class);
        	startActivity(i1);
           break;
      
        case R.id.button2:
        	Intent i2 = new Intent(this, Rate.class);
            startActivity(i2);
           break;
       
        }
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rating_form, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
