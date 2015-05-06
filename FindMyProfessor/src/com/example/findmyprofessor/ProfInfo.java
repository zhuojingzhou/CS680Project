package com.example.findmyprofessor;

import java.util.ArrayList;

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
import android.widget.TextView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.*;
import android.database.sqlite.*;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.net.Uri;

public class ProfInfo extends Activity {
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	private EditText assignmentTextBox;
    private EditText knowledgeTextBox;
    private EditText accessibleTextBox;
    private EditText comments1;
    private EditText comments2;
    private EditText comments3;
    private TextView professorNameText;
    private TextView profUrl;
    private String profName;
    private Intent intent;
    private Professor professor;
    private Button goButton;
    int assignmentScore;
    int knowledgeScore;
    int accessibleScore;
    int aTotal = 0;
    int kTotal = 0;
    int accTotal = 0;
    ArrayList<String> commentList = new ArrayList<String>();
    ArrayList<Integer> ratingList;
    ArrayList<ProfessRating>  prRatings;
    int ratingCount = 0;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.prof_info_page);
		
		dbHelper = new DatabaseHelper(this);
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLException e) {
			Log.d("FindMyProfessor", "Database failed");
		}
		
		assignmentTextBox = (EditText)findViewById(R.id.txtAssignments);
		knowledgeTextBox = (EditText)findViewById(R.id.txtKnowledge);
		accessibleTextBox = (EditText)findViewById(R.id.txtAccessible);
		comments1 = (EditText)findViewById(R.id.txtComment1);
		comments2 = (EditText)findViewById(R.id.txtComment2);
		comments3 = (EditText)findViewById(R.id.txtComment3);
		professorNameText = (TextView)findViewById(R.id.txtProfNameSearch);
		profUrl = (TextView)findViewById(R.id.txtProfProfileSearch);
		goButton = (Button) findViewById(R.id.buttonInfo);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			profName = extras.getString("ProfessName");
		}
		
		professor = dbHelper.getProfByName(profName);
		Log.d("Professor result", professor.getName());
		professorNameText.setText(professor.getName());
		
		// Set button to open browser
		goButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				openBrowser();
			}
		});
		
//		assignmentScore = dbHelper.getAvgRating(profName, "assignments");
//		assignmentTextBox.setText(assignmentScore);
	    
//		ratingList = dbHelper.getOneRating(profName, "assignments");
//		assignmentTextBox.setText(ratingList.get(0));
		
		prRatings = dbHelper.getProfessorRating(profName);
		for (ProfessRating pr : prRatings) {
			aTotal += pr.getAssignments();
			kTotal += pr.getKnowledge();
			accTotal += pr.getAccessible();
			ratingCount++;
			commentList.add(pr.getComment().substring(0, 10) + "...");
			Log.d("test", pr.getProfName());
		}
		
		assignmentScore = aTotal/ratingCount;
		knowledgeScore = kTotal/ratingCount;
		accessibleScore = accTotal/ratingCount;
		assignmentTextBox.setText(String.valueOf(assignmentScore));
		knowledgeTextBox.setText(String.valueOf(knowledgeScore));
		accessibleTextBox.setText(String.valueOf(accessibleScore));
		comments1.setText(commentList.get(0));
		comments2.setText(commentList.get(1));
		comments3.setText(commentList.get(2));
		
		
		
	}
	
//	@Override
//	public void onResume() {
//	    super.onResume();  // Always call the superclass method first
//
//	    prRatings = dbHelper.getProfessorRating(profName);
//		for (ProfessRating pr : prRatings) {
//			aTotal += pr.getAssignments();
//			kTotal += pr.getKnowledge();
//			accTotal += pr.getAccessible();
//			ratingCount++;
//			commentList.add(pr.getComment().substring(0, 10));
//			Log.d("test", pr.getProfName());
//		}
//		
//		assignmentScore = aTotal/ratingCount;
//		knowledgeScore = kTotal/ratingCount;
//		accessibleScore = accTotal/ratingCount;
//		assignmentTextBox.setText(String.valueOf(assignmentScore));
//		knowledgeTextBox.setText(String.valueOf(knowledgeScore));
//		accessibleTextBox.setText(String.valueOf(accessibleScore));
//		comments1.setText(commentList.get(0));
//		comments2.setText(commentList.get(1));
//		comments3.setText(commentList.get(2));
//	}
	
	/** Open a browser on the URL specified in the text box */
	private void openBrowser() {
		Uri uri = Uri.parse(professor.getBioAddress());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prof_info, menu);
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
