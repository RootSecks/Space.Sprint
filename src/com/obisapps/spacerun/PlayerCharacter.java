package com.obisapps.spacerun;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.TypedValue;

public class PlayerCharacter {


	private Bitmap bitmap;
	
	private Bitmap jumpBitmap;
	
	private int playerX; //Player X Coord
	private int playerY; //Player Y Coord
	
	private Rect scopeRectangle; //Rectangle used to cut the sprite image up
	
	private int playerFps; //The amount of FPS for the player render. defined by the time increment / fps, see init method
	
	private int framesNum; //The amount of frames in the animation
	
	private int currentFrame; //The current frame being rendered
	
	private long frameTimer; //A counter for te time it takes to render stuff
	
	private int playerHeight; //Height of the sprite
	private int playerWidth; //Width of the sprite.
	
	private int playerSpeed; //An int that is added to the y coord to make the player move
	
	private boolean playerJumping; //Boolean to determine wheter or not the player is currently jumping

	private boolean isDead;
	
	private boolean stillJumping;
	
	SoundManager soundManager;
	
	float dpscale;

	Context context;
	
	
	//CONSTRUCTOR
	public PlayerCharacter(Context pContext) {
    	//Log.d(TAG, "Constructor");
		scopeRectangle = new Rect(0,0,0,0); //Initalize the scope rectangle
		frameTimer = 0; //0 out the time
		currentFrame = 0; //0 out the current frame
		playerJumping = false; //Set the jumping to false
		context = pContext;
		

	}
	
	public void init(Canvas canvas, Bitmap jBitmap, Bitmap bitMap, int height, int width, int fPS, int frameCount, int pSpeed, SoundManager pSoundManager) {
		
		
		dpscale = context.getResources().getDisplayMetrics().density;
		
		//playerX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, resources.getDisplayMetrics());
		playerX = (int)(100 * dpscale);
		playerY = (int) ((canvas.getHeight()/2 - (20 * dpscale)));
		
		soundManager = pSoundManager;
		
		jumpBitmap = jBitmap;
		
		bitmap = bitMap;
		playerHeight = height;
		playerWidth = width;
		
		scopeRectangle.top = 0;
		scopeRectangle.bottom = playerHeight;
		scopeRectangle.left = 0;
		scopeRectangle.right = playerWidth;
		
		playerFps = (1000/fPS);
		
		framesNum = frameCount;
		
		playerSpeed = pSpeed;
		
		
	}
	
	public void update(long gameTime, Canvas canvas, PhysicsEngine physicsEngine) {

	    	//Animation ticker
	    	if (gameTime > frameTimer + playerFps) {
				
				frameTimer = gameTime;
				currentFrame +=1;
	
				if (currentFrame >= framesNum) {
					
					currentFrame = 0;
					
				}
			
			}	


		
		//Move the scope rectangle to the next frame of animation
		scopeRectangle.left = currentFrame * playerWidth;
		scopeRectangle.right = scopeRectangle.left + playerWidth;
			

	}
		
	public void draw(Canvas canvas) {
		

    	
		Rect dest = new Rect(getX(), getY(), (getX() + getWidth()), (getY() + getHeight()));
		
		if (isStillJumping()) {
		canvas.drawBitmap(jumpBitmap, scopeRectangle, dest, null);
		} else {
		canvas.drawBitmap(bitmap, scopeRectangle, dest, null);
		}
	}
	
	public void jump() {

		if (!playerJumping) {
			soundManager.jumpSound();
			setJumping(true);
			setStillJumping(true);
		}

	}
	

	//Getters and Setters	
	public boolean isJumping() {
		return playerJumping;
	}

	public void setJumping(boolean pJumping) {
			playerJumping = pJumping;
	}
	
	public int getY() {
		return playerY;
	}

	public int getX() {
		return playerX;
	}

	public int getHeight() {
		return playerHeight;
	}
	
	public int getWidth() {	
		return playerWidth;
	}
	
	public int getPlayerSpeed() {
		
		return playerSpeed;
	}
	
	public void setX(int x) {
		this.playerX = x;	
	}
	
	public void setY(int y) {

		this.playerY = y;
	}
	


	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		
		this.isDead = isDead;
	}


	public boolean isStillJumping() {
		return stillJumping;
	}

	public void setStillJumping(boolean stillJumping) {
		this.stillJumping = stillJumping;
	}

		
}
