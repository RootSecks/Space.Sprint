package com.obisapps.spacerun;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class GameActivity extends Activity {
	
	//private static final String TAG = GameActivity.class.getSimpleName();
	
	 HighScoreDBAdapter dbHelper;

	@Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
 
        GameView gameView = new GameView(this);
        
        this.setContentView(gameView);
        
        //Log.d(TAG, "View Added");
              
    }
	
	@Override
	protected void onResume() {
		
		super.onResume();

		
	}
	
    @Override
    protected void onDestroy() {
    	//Log.d(TAG, "DESTROY");
       	super.onDestroy();
       	    	
    	this.finish();
    	
    	System.exit(0);
    	    	
    }
    
    @Override
    protected void onPause() {
    	
    	super.onPause();
    	
    	this.finish();
    	
    	System.exit(0);
    	
    }
 
    @Override
    protected void onStop() {
    	
    	//Log.d(TAG, "STOPPING");
    	super.onStop();
    	
    	this.finish();
    	
    	System.exit(0);
    	
    }
    
}