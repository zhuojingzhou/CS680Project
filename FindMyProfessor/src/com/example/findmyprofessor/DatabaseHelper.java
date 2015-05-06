package com.example.findmyprofessor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String LOG = "DatabaseHelper";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "findMyProfessor";
	
	// Table names
	private static final String T_PROFESSOR = "professors";
	private static final String T_USER = "users";
	private static final String T_PROFESSOR_RATING = "professorRatings";
	
	// id column
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";
	
	// Professor table
	private static final String KEY_NAME = "name";
	private static final String KEY_BIOADDRESS = "bioAddress";
	private static final String KEY_DEPARTMENT = "department";
	
	// User table
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASSWORD = "password";
	
	// ProfessorRating table
	private static final String KEY_PROFNAME = "profName";
	private static final String KEY_USEREMAIL = "userEmail";
	private static final String KEY_COMMENT = "comment";
	private static final String KEY_ASSIGNMENTS = "assignments";
	private static final String KEY_KNOWLEDGE = "knowledge";
	private static final String KEY_ACCESSIBLE = "accessible";
	
	// Professor table create
	private static final String CREATE_TABLE_PROFESSOR = "CREATE TABLE "
            + T_PROFESSOR + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_BIOADDRESS + " TEXT," + KEY_DEPARTMENT + " TEXT" + ");";

	private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + T_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL
            + " TEXT," + KEY_PASSWORD + " TEXT" + ");";
	
	private static final String CREATE_TABLE_PROFESSORRATING = "CREATE TABLE "
            + T_PROFESSOR_RATING + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PROFNAME
            + " TEXT," + KEY_USEREMAIL + " TEXT," + KEY_COMMENT + " TEXT," + KEY_ASSIGNMENTS 
            + " INTEGER," + KEY_KNOWLEDGE + " INTEGER," + KEY_ACCESSIBLE 
            + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ");";
	
	public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
    public void onCreate(SQLiteDatabase db) {
 
		db.execSQL("DROP TABLE IF EXISTS " + T_PROFESSOR);
        db.execSQL("DROP TABLE IF EXISTS " + T_USER);
        db.execSQL("DROP TABLE IF EXISTS " + T_PROFESSOR_RATING);
		
        // creating required tables
        db.execSQL(CREATE_TABLE_PROFESSOR);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PROFESSORRATING);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//    	if (oldVersion >= newVersion) return;
    	
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + T_PROFESSOR);
        db.execSQL("DROP TABLE IF EXISTS " + T_USER);
        db.execSQL("DROP TABLE IF EXISTS " + T_PROFESSOR_RATING);
 
        // create new tables
        onCreate(db);
    }
    
    // Add professor
    public void addProfessor(Professor prof) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_NAME, prof.getName());
    	values.put(KEY_BIOADDRESS, prof.getBioAddress());
    	values.put(KEY_DEPARTMENT, prof.getDepartment());
    	db.insert(T_PROFESSOR, null, values);
    	Log.d("FindMyProfessor", prof.getName() + " added");
    	db.close();
    }
	
    // Update professor name and department in database
  	public void updateProfessor(Professor oldVersion, Professor newItem){
  		SQLiteDatabase db = this.getWritableDatabase();
  		ContentValues values = new ContentValues();
        values.put(KEY_NAME, newItem.getName());
        values.put(KEY_DEPARTMENT, newItem.getDepartment());
        db.update(T_PROFESSOR, values, KEY_NAME + "=?", new String[] {oldVersion.getName()});
        db.update(T_PROFESSOR, values, KEY_DEPARTMENT + "=?", new String[] {oldVersion.getDepartment()});
        Log.d("FindMyProfessor", oldVersion.getName() + " updated");
        db.close();
  	}
  	
  	// Get all professors
  	public ArrayList<Professor> getProfessorList() {
  		SQLiteDatabase db = this.getWritableDatabase();
  		Cursor cursor = db.query(T_PROFESSOR, new String[] {KEY_NAME, KEY_BIOADDRESS, KEY_DEPARTMENT},
  				null, null, null, null, KEY_NAME);
  		ArrayList<Professor> pList = new ArrayList<Professor>();
  		while(cursor.moveToNext()) {
  			Professor p = new Professor();
  			p.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
  			p.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
  			p.setBioAddress(cursor.getString(cursor.getColumnIndex(KEY_BIOADDRESS)));
  			p.setDepartment(cursor.getString(cursor.getColumnIndex(KEY_DEPARTMENT)));
  			pList.add(p);
  		}
  		db.close();
  		return pList;
  	}
  	
  	// Get professor by name
  	public Professor getProfByName(String name) {
  		SQLiteDatabase db = this.getReadableDatabase();
  		String selectQuery = "SELECT * FROM " + T_PROFESSOR + " WHERE " + KEY_NAME
  				+ " ='"  + name + "'";
  		Cursor cursor = db.rawQuery(selectQuery, null);
  		if (cursor != null) {
  			cursor.moveToFirst();
  		}
  		Professor p = new Professor();
		p.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
		p.setBioAddress(cursor.getString(cursor.getColumnIndex(KEY_BIOADDRESS)));
		p.setDepartment(cursor.getString(cursor.getColumnIndex(KEY_DEPARTMENT)));
  		return p;
  	}
  	
  	// Get professor by department
  	public ArrayList<Professor> getProfByDpt(String dpt) {
  		SQLiteDatabase db = this.getReadableDatabase();
  		String selectQuery = "SELECT * FROM " + T_PROFESSOR + " WHERE " + KEY_DEPARTMENT
  				+ " ='"  + dpt + "'";
  		Cursor cursor = db.rawQuery(selectQuery, null);
  		ArrayList<Professor> profList = new ArrayList<Professor>();
  		while(cursor.moveToNext()) {
  			Professor p = new Professor();
  			p.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
  			p.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
  			p.setBioAddress(cursor.getString(cursor.getColumnIndex(KEY_BIOADDRESS)));
  			p.setDepartment(cursor.getString(cursor.getColumnIndex(KEY_DEPARTMENT)));
  			profList.add(p);
  		}
  		db.close();
  		return profList;
  	}
  	
 // Get professors' name by department
   	public ArrayList<String> getProfNamesByDpt(String dpt) {
   		SQLiteDatabase db = this.getReadableDatabase();
   		String selectQuery = "SELECT * FROM " + T_PROFESSOR + " WHERE " + KEY_DEPARTMENT
   				+ " ='"  + dpt + "'";
   		Cursor cursor = db.rawQuery(selectQuery, null);
   		ArrayList<String> nameResults = new ArrayList<String>();
   		while(cursor.moveToNext()) {
   			String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
   			nameResults.add(name);
   		}
   		db.close();
   		return nameResults;
   	}
  	
  	// Add User
    public void addUser(User user) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_EMAIL, user.getEmail());
    	values.put(KEY_PASSWORD, user.getPassword());
    	db.insert(T_USER, null, values);
    	Log.d("FindMyProfessor", user.getEmail() + " added");
    	db.close();
    }
	
    // Update user email and password in database
  	public void updateEmailAndPassword(User oldVersion, User newItem){
  		SQLiteDatabase db = this.getWritableDatabase();
  		ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, newItem.getEmail());
        values.put(KEY_PASSWORD, newItem.getPassword());
        db.update(T_USER, values, KEY_EMAIL + "=?", new String[] {oldVersion.getEmail()});
        db.update(T_USER, values, KEY_PASSWORD + "=?", new String[] {oldVersion.getPassword()});
        Log.d("FindMyProfessor", oldVersion.getEmail() + " updated");
        db.close();
  	}
  	
  	// Get all users
  	public ArrayList<User> getUserList() {
  		SQLiteDatabase db = this.getWritableDatabase();
  		Cursor cursor = db.query(T_USER, new String[] {KEY_EMAIL, KEY_PASSWORD},
  				null, null, null, null, KEY_EMAIL);
  		ArrayList<User> userList = new ArrayList<User>();
  		while(cursor.moveToNext()) {
  			User u = new User();
//  			u.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));;
  			u.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
  			u.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
  			userList.add(u);
  		}
  		db.close();
  		return userList;
  	}
  	
  	// Add new rating
  	public void addProfessorRating(ProfessRating entry) {
  		SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_PROFNAME, entry.getProfName());
    	values.put(KEY_USEREMAIL, entry.getUserEmail());
    	values.put(KEY_COMMENT, entry.getComment());
    	values.put(KEY_ASSIGNMENTS, entry.getAssignments());
    	values.put(KEY_KNOWLEDGE, entry.getKnowledge());
    	values.put(KEY_ACCESSIBLE, entry.getAccessible());
    	values.put(KEY_CREATED_AT, getDateTime());
    	db.insert(T_PROFESSOR_RATING, null, values);
    	Log.d("FindMyProfessor", entry.getProfName() + " rating added");
    	db.close();
  	}
  	
  	// Get all ratings
  	public ArrayList<ProfessRating> getProfessorRating(String name) {
  		SQLiteDatabase db = this.getReadableDatabase();
  		String selectQuery = "SELECT * FROM " + T_PROFESSOR_RATING + " WHERE " + KEY_PROFNAME
  				+ " ='" + name + "'" + " ORDER BY " + KEY_CREATED_AT + " DESC";
  		Cursor cursor = db.rawQuery(selectQuery, null);
  		ArrayList<ProfessRating> ratingList = new ArrayList<ProfessRating>();
  		while(cursor.moveToNext()) {
  			ProfessRating pr = new ProfessRating();
//  			pr.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
  			pr.setProfName(cursor.getString(cursor.getColumnIndex(KEY_PROFNAME)));
  			pr.setUserEmail(cursor.getString(cursor.getColumnIndex(KEY_USEREMAIL)));
  			pr.setComment(cursor.getString(cursor.getColumnIndex(KEY_COMMENT)));;
  			pr.setAssignments(cursor.getInt(cursor.getColumnIndex(KEY_ASSIGNMENTS)));
  			pr.setKnowledge(cursor.getInt(cursor.getColumnIndex(KEY_KNOWLEDGE)));
  			pr.setAccessible(cursor.getInt(cursor.getColumnIndex(KEY_ACCESSIBLE)));
  			ratingList.add(pr);
  		}
  		db.close();
  		return ratingList;
  	}
  	
  	// Get one rating from a professor
  	public ArrayList<Integer> getOneRating(String name, String category) {
  		SQLiteDatabase db = this.getReadableDatabase();
  		String selectQuery = "SELECT " + category + " FROM " + T_PROFESSOR_RATING + " WHERE " + KEY_PROFNAME
  				+ " ='" + name + "'";
  		Cursor cursor = db.rawQuery(selectQuery, null);
  		ArrayList<Integer> ratingList = new ArrayList<Integer>();
  		while(cursor.moveToNext()) {
  			int temp = cursor.getInt(cursor.getColumnIndex(category));
  			ratingList.add(temp);
  		}
  		db.close();
  		return ratingList;
  	}
  	
  	// Get average rating from a professor
  	public int getAvgRating(String profName, String category) {
  		int avg = 0;
  		SQLiteDatabase db = this.getReadableDatabase();
  		Cursor cursor;
  		
  		switch (category) {
  			case KEY_ASSIGNMENTS:
  				String selectQuery = "SELECT ROUND(AVG(assignments),0) AS " + "'" + "X" + "'"
  						+ " FROM " + T_PROFESSOR_RATING + " WHERE " + KEY_PROFNAME
  				+ " ='" + profName + "'";
  				
  				cursor = db.rawQuery(selectQuery, null);
  		  		if (cursor != null) {
  		  			cursor.moveToFirst();
  		  		}
  		  		
  		  		avg = (int)cursor.getDouble(cursor.getColumnIndex("X"));
  		  		break;
  			case KEY_KNOWLEDGE:
  				String selectQuery2 = "SELECT ROUND(AVG(knowledge),0) AS " + "'" + "X" + "'"
  						+ " FROM " + T_PROFESSOR_RATING + " WHERE " + KEY_PROFNAME
  				+ " ='" + profName + "'";
  				
  				cursor = db.rawQuery(selectQuery2, null);
  		  		if (cursor != null) {
  		  			cursor.moveToFirst();
  		  		}
  		  		
  		  		avg = (int)cursor.getDouble(cursor.getColumnIndex("X"));
  		  		break;
  			case KEY_ACCESSIBLE:
  				String selectQuery3 = "SELECT ROUND(AVG(accessible),0) AS " + "'" + "X" + "'"
  						+ " FROM " + T_PROFESSOR_RATING + " WHERE " + KEY_PROFNAME
  				+ " ='" + profName + "'";
  				
  				cursor = db.rawQuery(selectQuery3, null);
  		  		if (cursor != null) {
  		  			cursor.moveToFirst();
  		  		}
  		  		
  		  		avg = (int)cursor.getDouble(cursor.getColumnIndex("X"));
  		  		break;
  		  	default:
  		  		avg = 0;
  		  		break;
  				
  		}
  		
  		return avg;
  	}
	
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
