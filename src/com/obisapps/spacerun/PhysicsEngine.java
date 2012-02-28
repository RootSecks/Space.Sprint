package com.obisapps.spacerun;

import android.graphics.Canvas;

public class PhysicsEngine {
	
	
	private LevelCharacter levelCharacter;
	
	
	private int getRectIndex; 
	
	private int jumpHeight = 10;
	private int jumpSpeed;
	private int jumpGravity = 1;
	private int terminalVelocity = -50;
	
	private long jumpPressTimer;
	private long maxJumpTimeMillis = 500;
	
	private int herpderp;
	
	private int scrollSpeed = 10;
	
	public void init(int pGravity, int pSpeed, LevelCharacter lCharacter) {
		levelCharacter = lCharacter;
		jumpSpeed = 0;
	}
	
	
		
	public boolean onFloor(PlayerCharacter playerCharacter) {
		
		getRectIndex = getActiveRect((playerCharacter.getX()));
	
		if (levelCharacter.levelRects.get(getRectIndex).width() >= 11) {
			
			if (playerCharacter.getY() + playerCharacter.getHeight() == levelCharacter.mapRects.get(getRectIndex).top) {
				
				playerCharacter.setStillJumping(false);
				return true;
				
			} else {
				
				return false;
			}
			
		} else {
			
			return false;
		}
		
		
	}
	
	
	private int getActiveRect(int playerX) {
		
		for (int u = 0; u <= levelCharacter.totalBlocksBuffer; u++) {
			if (levelCharacter.mapRects.get(u).contains(playerX, levelCharacter.mapRects.get(u).top)) {
				return u;
			}
		}
		return (Integer) null;
		
	}
	
	



	public void update(PlayerCharacter playerCharacter, LevelCharacter lCharacter, Canvas canvas, GameEngine gEngine) {
		
		int getIndex = getActiveRect(playerCharacter.getX() - scrollSpeed);
		
		if (onFloor(playerCharacter) && playerCharacter.isJumping()) {
			
			jumpSpeed = jumpHeight;			
			playerCharacter.setJumping(false);
			playerCharacter.setStillJumping(true);
			jumpPressTimer = System.currentTimeMillis();
			herpderp = scrollSpeed;
			
		} else if ((!onFloor(playerCharacter)) && (!playerCharacter.isJumping()) && (playerCharacter.isStillJumping()) && (gEngine.isJumpPushed()) && (System.currentTimeMillis() - jumpPressTimer < maxJumpTimeMillis)) {
			
			jumpSpeed = jumpHeight;
			herpderp = scrollSpeed;
			
		} else if (onFloor(playerCharacter)) {
			
			jumpSpeed = 0;
			herpderp = scrollSpeed;
			
		} else {
			
			jumpSpeed -= jumpGravity;
			
			if (lCharacter.levelRects.get(getIndex).width() >= 11) {
			if ((lCharacter.mapRects.get(getIndex).top < playerCharacter.getY() + playerCharacter.getHeight() - jumpSpeed) && (lCharacter.mapRects.get(getIndex).bottom > playerCharacter.getY() - jumpSpeed)) {
					if ((lCharacter.mapRects.get(getIndex).left > playerCharacter.getX() + playerCharacter.getWidth()) && (lCharacter.mapRects.get(getIndex).left < playerCharacter.getX() + playerCharacter.getWidth())) {
						herpderp = 0 - scrollSpeed;
					} else {
						jumpSpeed = (playerCharacter.getY() + playerCharacter.getHeight()) - lCharacter.mapRects.get(getIndex).top;
					}
				} else {
					herpderp = scrollSpeed;
				}
			} else {
				
				herpderp = scrollSpeed;
			}
			
			
		}
		
			
	

		for (int g = 0; g <= lCharacter.getBlocksBuffer(); g++) {
			
			lCharacter.mapRects.get(g).left -= herpderp;
			lCharacter.mapRects.get(g).right -= herpderp;
			
			lCharacter.mapRects.get(g).top += jumpSpeed;
			lCharacter.mapRects.get(g).bottom += jumpSpeed;
		}
		

		//derp
		if (jumpSpeed <= terminalVelocity) {
			
			playerCharacter.setY(playerCharacter.getY() + 10);
			
			if (jumpSpeed<= (terminalVelocity - 50)) {
				
				playerCharacter.setDead(true);
				
			}
			
			
		}
		
		
		
	}

	
	
	public void increaseScrollSpeed(int speedIncrement) {
		
		scrollSpeed += speedIncrement;
		
	}
	
	public void setScrollSpeed(int setSpeed) {
		
		scrollSpeed = setSpeed;
		
	}
	
	public int getScrollSpeed() {
		
		return scrollSpeed;
	}
	
	
}
