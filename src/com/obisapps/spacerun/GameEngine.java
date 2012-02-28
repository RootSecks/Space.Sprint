package com.obisapps.spacerun;




import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.MotionEvent;

public class GameEngine {
	
	//private static final String TAG = GameEngine.class.getSimpleName();

	private int distanceCounter;
	
	//Bitmap for the Character 
	Bitmap bitmap;
	
	
	private int gameState;
	
	PlayerCharacter playerCharacter;
	LevelCharacter levelCharacter;
	PhysicsEngine physicsEngine;
	SoundManager soundManager;
	SpaceBackground spaceBackground;

	
	private boolean firsttime;
	
	private int gameScore;
	private int scoreBaseUnit = 1;
	private int scoreOffset;
	
	private Paint instructPaint = new Paint();
	private Paint instructPaintTwo = new Paint();
	private Paint scorePaint = new Paint();
	private Paint introPaint = new Paint();
	
	private Paint menuPaint = new Paint();
	private Paint writingPaint = new Paint();
	
	private Paint textPaint = new Paint();
	
	private Paint yayPaint = new Paint();
	
	private boolean jumpPushed = false;
	
	private Cursor highScoreCursor;
	private int highScore;
	
	
	float dpscale;
	
	Context context;
	
	Canvas canvas;
	
	HighScoreDBAdapter dbHelper;
	
	Typeface titleTf;
	Typeface writingTf;
	
	int alphaVal = 255;
	int alphaValTwo = 0;
	
	public GameEngine(Context contextp) {
		

		context = contextp;
		firsttime = true;
		
		dpscale = context.getResources().getDisplayMetrics().density;
			
		playerCharacter = new PlayerCharacter(context);
		levelCharacter = new LevelCharacter(context);
		physicsEngine = new PhysicsEngine();
		soundManager = new SoundManager(context);
		spaceBackground = new SpaceBackground();


		setGameState(0);
		
		distanceCounter = 0;
		
		titleTf = Typeface.createFromAsset(context.getAssets(), "fonts/OrbitronM.otf");
		writingTf = Typeface.createFromAsset(context.getAssets(), "fonts/OrbitronL.otf");
		
		menuPaint.setColor(Color.WHITE);
	    menuPaint.setTextSize(40);
	    menuPaint.setTypeface(titleTf);
	    
	    writingPaint.setColor(Color.WHITE);
	    writingPaint.setTextSize(32);
	    writingPaint.setTypeface(writingTf);
	
	    scorePaint.setColor(Color.WHITE);
	    scorePaint.setTextSize(20);
	    scorePaint.setTypeface(writingTf);
	    
	    instructPaint.setColor(Color.WHITE);
	    instructPaint.setTextSize(20);
	    instructPaint.setTypeface(writingTf);
	    
	    instructPaintTwo.setColor(Color.WHITE);
	    instructPaintTwo.setTextSize(20);
	    instructPaintTwo.setTypeface(writingTf);
	    
	    introPaint.setColor(Color.WHITE);
	    introPaint.setTextSize(72);
	    
		yayPaint.setColor(Color.RED);
		yayPaint.setTextSize(25);
		yayPaint.setTypeface(titleTf);
	
		textPaint.setColor(Color.WHITE);	
		textPaint.setTextSize(20);
		textPaint.setTypeface(writingTf);
		
	
	}
	
	
	public void initalizeObjects(Canvas pCanvas) {
		
			this.setGameState(1);
			
			canvas = pCanvas;
			
			//Initalize audio engine
			soundManager.init();
				
			//Initalize the player character (Canvas, Bitmap, SpriteHeight, SpriteWidth, FPS, NoofFrames, Player Speed)
			playerCharacter.init(canvas,BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowjump) ,BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow), 30, 15, 15, 2, 5, soundManager);
			
			//Initalize the space background class
			spaceBackground.init(canvas, levelCharacter);
			
			//Initalize the level, adding the height, width, and passing a canvas object, a player object, context, and the number of level blocks that can be used
			levelCharacter.init(canvas, (canvas.getHeight()/2), canvas.getWidth(), playerCharacter, 4, 10, spaceBackground);
			
			//Initalize the Physics engine
			physicsEngine.init(5, 5, levelCharacter);
			
			
			
			
			
			
		
			

			
									
	}
	
	public void update(Canvas canvas, Long gameTime) {
		

		if (getGameState() == 1) {
			
			
			//Initalize ALL THE THINGS as long as this is the first time
			if (firsttime) {
		    	//Log.d(TAG, "FIRST TIME Variable True: Running init");
				this.initalizeObjects(canvas);
				this.firsttime = false;
				distanceCounter = 0;
				gameScore = 0;
				scoreOffset = 1;
				physicsEngine.setScrollSpeed(0);
			
				alphaVal = 255;
				alphaValTwo = 0;
				instructPaint.setAlpha(alphaVal);
				instructPaintTwo.setAlpha(alphaValTwo);
			
				//soundManager.playLoopSound(1);
				
				soundManager.playMusic();
				
			}
			
			
			if (!playerCharacter.isDead()) {
				
				//Update starfeild
				spaceBackground.update();
				//Update the level
				levelCharacter.update(canvas);
				//Update the player
				playerCharacter.update(gameTime, canvas, physicsEngine);
				//Update the level
				//levelCharacter.update(canvas);
				//Update the physics
				physicsEngine.update(playerCharacter, levelCharacter, canvas, this);	
				//Update the Player Character
			
	
				if (distanceCounter >= 100) {
					if (physicsEngine.getScrollSpeed() < 35) {
						
							physicsEngine.increaseScrollSpeed(1);
							
							levelCharacter.increaseHeightChange();

							if (levelCharacter.getBlankBlockWidth() < 500) {
							
								levelCharacter.increaseBlankBlockWidth();
							
							}

						scoreOffset += 1;
						
					}
				
					distanceCounter = 0;
				} else if ((distanceCounter >= 10) && (physicsEngine.getScrollSpeed() < 10)) {
					
					physicsEngine.increaseScrollSpeed(1);
					distanceCounter = 0;
					
				} else {
					
					distanceCounter += 1;
				}
				
				gameScore += (scoreBaseUnit + scoreOffset);
				
				
				
					
			} else if (playerCharacter.isDead()) {
					
				this.setGameState(2);
				distanceCounter = 0;
			}
			
			
		} else if (getGameState() == 0) {

			
		}
		
		
		if (getGameState() == 1) {
		
		    if ((gameScore > 400) && (gameScore < 600)) {
		    	
		    	if (alphaVal > 0) {
		    		alphaVal = alphaVal - 5;
		    		instructPaint.setAlpha(alphaVal);
		    	}
		    	
		    
		    }
		   
		    if (gameScore > 600 && (gameScore < 1100)) {
		    	
		
		    	if (alphaValTwo < 255) {
		    		alphaValTwo = alphaValTwo + 5;
		    		instructPaintTwo.setAlpha(alphaValTwo);   		
		    	}
		    			    	
		    }
		    
		    if (gameScore > 1100) {
		    	
		    	if (alphaValTwo > 0) {
		    		alphaValTwo = alphaValTwo - 5 ;
		    		instructPaintTwo.setAlpha(alphaValTwo);   		
		    	}
		    			    	
		    	
		    	
		    }
		    
		  
	    
		}
		
		
	}	
	
	public void draw(Canvas canvas) {
		

		if (getGameState() == 1) {
			
			if (!playerCharacter.isDead()) {
				
			    levelCharacter.draw(canvas);	
				playerCharacter.draw(canvas);
			    
			    canvas.drawText("Points: "  + Integer.toString(gameScore), (canvas.getWidth() / 2 + (100 * dpscale)), (40 * dpscale), scorePaint);
			    

			    
			    canvas.drawText("<Touch to Jump>", (50 * dpscale), canvas.getHeight() / 2 - (50 * dpscale), instructPaint);
			    canvas.drawText("<Hold to Jump HIGHER!>", (40 * dpscale), canvas.getHeight() / 2 - (50 * dpscale), instructPaintTwo);
			    
			    
			}
			
		} else if (getGameState() == 2) {
			
			drawGameOver(canvas);		
			
		} else if (getGameState() == 0) {
			
			dbHelper = new HighScoreDBAdapter(context);
			dbHelper.openToRead();
			highScoreCursor = dbHelper.fetchAllScores();
			
			if (highScoreCursor.moveToFirst()) {
				highScore = highScoreCursor.getInt(highScoreCursor.getColumnIndex("score"));
			} else {
				
				highScore = 0;
				
			}
			
			highScoreCursor.close();
			
			
			dbHelper.close();
						
			canvas.drawText("Space.Sprint", canvas.getWidth() / 2 - (100 * dpscale), canvas.getHeight() / 2 - (50 * dpscale), menuPaint);
						
			canvas.drawText("Touch to Start", canvas.getWidth() / 2 - (100 * dpscale), canvas.getHeight() / 2 + (50 * dpscale), menuPaint);
			
			canvas.drawText("Current High Score: " + highScore, canvas.getWidth() / 2, canvas.getHeight() / 2 + (75 * dpscale), textPaint);
			
		}


 
	}	
	

	private void drawGameOver(Canvas canvas) {
		
		
		soundManager.stopMusic();
		
		canvas.drawColor(Color.BLACK);
		
      
		dbHelper = new HighScoreDBAdapter(context);
		dbHelper.openToRead();
		highScoreCursor = dbHelper.fetchAllScores();
		
		if (highScoreCursor.moveToFirst()) {
			highScore = highScoreCursor.getInt(highScoreCursor.getColumnIndex("score"));
		} else {
			
			highScore = 0;
			
		}

		highScoreCursor.close();
		dbHelper.close();
		
		if (gameScore > highScore) {
			
			dbHelper = new HighScoreDBAdapter(context);
			dbHelper.openToWrite();
			dbHelper.deleteAll();
			dbHelper.createScore(gameScore);
			highScore = gameScore;
			dbHelper.close();
			

		}
		
		if (highScore == gameScore) {
			

			canvas.drawText("NEW HIGH SCORE!", (100 * dpscale), (50 * dpscale), yayPaint);
			
		}
	
		canvas.drawText("Player.Dead", canvas.getWidth() / 2 - (100 * dpscale), canvas.getHeight() / 2 - (50 * dpscale), menuPaint);
		
		canvas.drawText("Final Score: " + Integer.toString(gameScore), canvas.getWidth() / 2 - (100 * dpscale), canvas.getHeight() / 2, textPaint);
		
		canvas.drawText("High Score: " + Integer.toString(highScore), canvas.getWidth() / 2 - (100 * dpscale), canvas.getHeight() / 2 + (20 * dpscale), textPaint);
		
		canvas.drawText("Touch to TRY AGAIN", canvas.getWidth() / 2 - (100 * dpscale), canvas.getHeight() / 2 + (80 * dpscale), textPaint);
	}

	public void onTouchEvent(MotionEvent event) {
		
   	
    	if (getGameState() == 1) {
    		

    		if (event.getAction() == MotionEvent.ACTION_DOWN) {
    			

		    	setJumpPushed(true);
		    	
		    	playerCharacter.jump();
				
				
				
			} 
    		
    		if (event.getAction() == MotionEvent.ACTION_UP) {
    			
    			setJumpPushed(false);
    		}
    		
    	} else if (getGameState() == 2) {
    		

    		if (event.getAction() == MotionEvent.ACTION_DOWN) {

	
    					firsttime = true;
    					playerCharacter.setDead(false);
    					playerCharacter.setJumping(false);
    					levelCharacter.setDropStatus(false);
    					gameScore = 0;
    					setGameState(1);

    		}
    		
    	} else if (getGameState() == 0) {
    		

    		if (event.getAction() == MotionEvent.ACTION_DOWN) {
    			
    			
    			this.startGame();
    			

    			
    		}   		
    		
    	}
  
	}
	
	
	public void startGame() {
		setGameState(1);
		setFirstTime(true);
	}
	
	
	public void setGameState(int pGameState) {
		
		gameState = pGameState;
		
	}
	
	
	public int getGameState() {
		
		return gameState;
	}

	public void setFirstTime(boolean gFirstTime) {
		
		firsttime = gFirstTime;
	}
	
	public boolean isJumpPushed() {
		
		return jumpPushed;
		
	}
	
	public void setJumpPushed(boolean jPushed) {
		
		jumpPushed = jPushed;
		
	}
	
}
