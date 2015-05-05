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
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{
	private LinearLayout layoutToAnimate1;
	private Button button1;
	private Button button2;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Setting up database -----------------------------------------------------------------
		dbHelper = new DatabaseHelper(this);
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLException e) {
			Log.d("FindMyProfessor", "Database failed");
		}
		
		db.delete("professors", null, null);
		db.delete("users", null, null);
		db.delete("professorRatings", null, null);
		
		// Add professors
		Professor p1 = new Professor("James Pepe", "https://faculty.bentley.edu/details.asp?uname=jpepe", "CIS");
		Professor p2 = new Professor("Wendy Lucas", "https://faculty.bentley.edu/details.asp?uname=wlucas", "CIS");
		Professor p3 = new Professor("Adrian Song", "https://faculty.bentley.edu/details.asp?uname=jpepe", "Art");
		Professor p4 = new Professor("Yang Xue", "https://faculty.bentley.edu/details.asp?uname=wlucas", "Art");
		dbHelper.addProfessor(p1);
		dbHelper.addProfessor(p2);
		dbHelper.addProfessor(p3);
		dbHelper.addProfessor(p4);
		
		// Add users
		User u1 = new User("admin@example.com", "test123");
		User u2 = new User("sampleuser@gmail.com", "test123");
		dbHelper.addUser(u1);
		dbHelper.addUser(u2);
		
		// Add rating
		ProfessRating r1 = new ProfessRating("James Pepe", "z@gmail.com", 
				"Very kind, knowledgeable and helpful professor, relatively easy assignments", 5, 5, 5);
		ProfessRating r2 = new ProfessRating("James Pepe", "x@test.com", 
				"Testing comments2", 5, 5, 5);
		ProfessRating r3 = new ProfessRating("James Pepe", "y@gmail.com", 
				"Testing comments1", 5, 5, 5);
		ProfessRating r4 = new ProfessRating("Wendy Lucase", "test@test.com", 
				"Nice and accessible, relatively tough assignments", 3, 5, 5);
		ProfessRating r5 = new ProfessRating("Wendy Lucase", "test@test.com", 
				"Very well organized, knows her stuff", 5, 5, 5);
		ProfessRating r6 = new ProfessRating("Wendy Lucase", "test@test.com", 
				"This is just a test", 1, 1, 1);
		
		dbHelper.addProfessorRating(r1);
		dbHelper.addProfessorRating(r2);
		dbHelper.addProfessorRating(r3);
		dbHelper.addProfessorRating(r4);
		dbHelper.addProfessorRating(r5);
		dbHelper.addProfessorRating(r6);
		
		dbHelper.close();
		// End of setting up database -----------------------------------------------------------
		
		
		layoutToAnimate1 = (LinearLayout)findViewById(R.id.part2);
		// Load the appropriate animation and set listener
        Animation an1 =  AnimationUtils.loadAnimation(this, R.anim.snazzyintro);
        an1.setAnimationListener(new MyAnimationListener());
        
        // Start the animation
        layoutToAnimate1.startAnimation(an1);
    	button1 = (Button) findViewById(R.id.button1);
    	button1.setOnClickListener(this);
    	button2 = (Button) findViewById(R.id.button2);
    	button2.setOnClickListener(this); 
	} 
	class MyAnimationListener implements Animation.AnimationListener {

		public void onAnimationEnd(Animation animation) {
						
			layoutToAnimate1.setVisibility(View.VISIBLE);
	    }
		

		public void onAnimationRepeat(Animation animation) {
			// what to do when animation loops 
		}

		public void onAnimationStart(Animation animation) {			
			//what to do when animation stops
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) { 
        case R.id.button1:
        	Intent i1 = new Intent(this, Search.class);
            startActivity(i1);
           break;
      
        case R.id.button2:
        	Intent i2 = new Intent(this, Rate.class);
            startActivity(i2);
           break;
       
        }
		
	}
		

	
	
}
