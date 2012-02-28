package com.obisapps.spacerun;

import android.graphics.Canvas; 
import android.view.SurfaceHolder;

public class RenderThread extends Thread{
	
	//private static final String TAG = RenderThread.class.getSimpleName();
	
	private final static int maxFPS = 50;
	private final static int maxFrameSkips = 5;
	private final static int framePeriod = 1000 / maxFPS;
	
	private SurfaceHolder surfaceHolder;
	
	private GameView gameView;
	
	private boolean running;
	
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public RenderThread(SurfaceHolder surfaceHolder, GameView gameView) {
		super();
		
		this.surfaceHolder = surfaceHolder;
		
		this.gameView = gameView;
	
	}

    @Override
    public void run() {
    	Canvas canvas;
    	//Log.d(TAG, "Starting Game Loop");
    	
    	long beginTime;
    	long timeDiff;
    	int sleepTime;
    	int framesSkipped;
    	
    	sleepTime = 0;
    	
    	while (running) {
    		canvas = null;
    		
    		try {
    			canvas = this.surfaceHolder.lockCanvas();
    				
    				synchronized (surfaceHolder) {
    				
    					beginTime = System.currentTimeMillis();
    					framesSkipped = 0;
    				
    			    	//Log.d(TAG, "Running Update");
    					this.gameView.update(canvas, System.currentTimeMillis());
    					//Log.d(TAG, "Running Render");
    					this.gameView.render(canvas);
    					
    					timeDiff = (System.currentTimeMillis() - beginTime);	
    					sleepTime = (int) (framePeriod - timeDiff);
    					
    					
    					if (sleepTime > 0) {					
    						try {						
    							Thread.sleep(sleepTime);						
    						} catch (InterruptedException e) {
					
    						}
			
    					}	
    					while (sleepTime < 0 && framesSkipped < maxFrameSkips) {		
    						this.gameView.update(canvas, System.currentTimeMillis());
    						sleepTime += framePeriod;
    						framesSkipped ++;	
    					}			
    				} 	    		
    			} finally {
    				if (canvas != null) {			
    					surfaceHolder.unlockCanvasAndPost(canvas);				
    				}			
    			}		
    		}	
      	}

}