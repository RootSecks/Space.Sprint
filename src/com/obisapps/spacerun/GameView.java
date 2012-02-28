package com.obisapps.spacerun;



import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	//private static final String TAG = GameView.class.getSimpleName();
    private RenderThread thread;
    private GameEngine gameEngine;
    

    public GameView(Context context) {
    	super(context);
    	
    	getHolder().addCallback(this);
    	
    	gameEngine = new GameEngine(context);
    	
    	thread = new RenderThread(getHolder(), this);
    	
    	setFocusable(true);
    	
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		
	}

    @Override 
    public void surfaceCreated(SurfaceHolder holder) {
    	
    	thread.setRunning(true);
    	
    	thread.start();
    	
    
    }

    @Override 
    public void surfaceDestroyed(SurfaceHolder holder) {
       
    	//Log.d(TAG, "Surface is being destroyed");
    	
    	boolean retry = true;
        //code to end 
        while (retry) {
            try {
                //code to kill Thread
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            	
            }
        }
        

    }
             
    public void render(Canvas canvas) {
    	gameEngine.draw(canvas); 	
    }
    
	public void update(Canvas canvas, Long gameTime) {
    	gameEngine.update(canvas, gameTime);	
	}

    public boolean onTouchEvent(MotionEvent event) {
    	
    	gameEngine.onTouchEvent(event);
    	
    	return true;
    	
    }
    

       
}
