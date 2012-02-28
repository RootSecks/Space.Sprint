package com.obisapps.spacerun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class HighScoreDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_CREATE = "create table highscores "
			+ "(_id integer primary key autoincrement, "
			+ "score int not null);";

	public HighScoreDatabaseHelper(Context context, String name,
			  CursorFactory factory, int version) {
		
		super(context, name, factory, version);

	}	

	
	@Override
	public void onCreate(SQLiteDatabase database) {
		
		database.execSQL(DATABASE_CREATE);
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		
		database.execSQL("DROP TABLE IF EXISTS highscores");
		database.execSQL(DATABASE_CREATE);
		
	}
	
}
