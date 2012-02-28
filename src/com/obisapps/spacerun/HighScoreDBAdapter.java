package com.obisapps.spacerun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class HighScoreDBAdapter {

	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_SCORE = "score";
	private static final String DB_TABLE = "highscores";
	private static final String DATABASE_NAME = "appdata";
	private static final int DATABASE_VERSION = 1;
	
	private Context context;
	
	private HighScoreDatabaseHelper dbHelper;
	private SQLiteDatabase db;

	

	public HighScoreDBAdapter(Context pContext) {
		
		context = pContext;
		
	}
	


	public HighScoreDBAdapter openToRead() throws SQLException {
		
		dbHelper = new HighScoreDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = dbHelper.getReadableDatabase();
		return this;
		
	}


	public HighScoreDBAdapter openToWrite() throws SQLException {
		
		dbHelper = new HighScoreDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = dbHelper.getWritableDatabase();
		return this;
		
	}

	public void close() {
		
		dbHelper.close();
		
	}
	

	public long createScore(int score) {
		
		ContentValues values = createContentValues(score);
		
		return db.insert(DB_TABLE, null, values);
		
	}
	
	public int deleteAll() {
		
		return db.delete(DB_TABLE, null, null);
		
	}

	
	public Cursor fetchAllScores() {
		
		String[] columns = new String[]{KEY_ROWID, KEY_SCORE};
		Cursor cursor = db.query(DB_TABLE, columns,null, null, null, null, null);

		return cursor;
	}
	
	
	
	private ContentValues createContentValues(int score) {
		ContentValues values = new ContentValues();
		values.put(KEY_SCORE, score);
		return values;
	}
}
