package com.obisapps.spacerun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class LevelCharacter {
	
	public int levelHeight;
	public int levelWidth;
	
	
	private boolean dropStatus = false;
	
	private Paint lolpaint = new Paint();

	List<Rect> mapRects = new ArrayList<Rect>();
	List<Rect> levelRectsArray = new ArrayList<Rect>();
	List<Rect> levelRects = new ArrayList<Rect>();
	
	
	
	Rect blankBlockRect;
	
	Paint paint = new Paint();
	
	Random rand = new Random();
	
	
	Paint platformPaint = new Paint();
	
	private int randMax;
	private int randNum;
	
	private int blankBlockRand;
	
	private int heightChangeProp;
	
	private int previousRectLeft;
	private int previousRectWidth;
	private int previousRectRight;
	private int previousRectTop;
	
	private int yOffset = 0;
	
	private int tickOver = 0;
	private int tickcounter = 1;
	
	private int blankBlockWidth = 300;
	
	float dpscale;
	
	//The number of block bitmaps avialbe to randomly use to generate level.s
	private int mapBlocksNum;
	
	public int totalBlocksBuffer = 10;
	
	private int scrollSpeed;
	
	private int randomHeightOffset;
	
	private int maxHeightOffset = 50;

	Context context;
	
	SpaceBackground spaceBackground;
	
	public LevelCharacter(Context pContext) {
		
		context = pContext;
		
		dpscale = context.getResources().getDisplayMetrics().density;
		
		//Left Top Right Bottom
		levelRectsArray.add(new Rect(0,0,(int) (700 * dpscale),(int) (4 * dpscale)));
		levelRectsArray.add(new Rect(0,0,(int) (600 * dpscale),(int) (4 * dpscale)));
		levelRectsArray.add(new Rect(0,0,(int) (300 * dpscale),(int) (4 * dpscale)));
		levelRectsArray.add(new Rect(0,0,(int) (500 * dpscale),(int) (4 * dpscale)));

		
		blankBlockRect = new Rect(0,0,1,1);
		
		heightChangeProp = 45;
		
		platformPaint.setColor(Color.WHITE);
		
		platformPaint.setAntiAlias(true);
		
		
	}
	
	public void init(Canvas canvas, int i, int width, PlayerCharacter pCharacter, int mBlocksNum, int levelScrollSpeed, SpaceBackground sBackground) {
	
	  yOffset = 0;
	  tickOver = 0;
	  tickcounter = 1;
	  blankBlockWidth = (int) (150 * dpscale);
	  
	  maxHeightOffset = (int) (100 * dpscale);
	  
	  totalBlocksBuffer = 10;
	  
		platformPaint.setColor(Color.WHITE);
	
		heightChangeProp = 45;
	  levelRects.clear();
	  mapRects.clear();
		
		spaceBackground = sBackground;
		
		setHeight((canvas.getHeight()/2));
		setWidth(canvas.getWidth());
		
		scrollSpeed = levelScrollSpeed;
		


		

		//Total number of blocks
		mapBlocksNum = mBlocksNum;
		

		randMax = (mapBlocksNum - 1);
		
		
		for (int x = 0; x <= totalBlocksBuffer; x ++ ) {
			
			
			randNum = rand.nextInt((randMax+1) - 0) + 0;
			blankBlockRand = rand.nextInt(((randMax + 1) + 1) - 0) + 0;
			
				if (x < 5) {
					
					levelRects.add(levelRectsArray.get(0));
					
					mapRects.add(new Rect(0, (pCharacter.getY() + pCharacter.getHeight()), levelRects.get(x).width(), pCharacter.getY() + pCharacter.getHeight() + levelRects.get(x).height()));

					
					previousRectLeft = mapRects.get(x).left;
					//previousRectWidth = levelRects.get(x).width();
					previousRectWidth = mapRects.get(x).width();

					previousRectRight = mapRects.get(x).right;
					previousRectTop = mapRects.get(x).top;

					
				} else {
					
					
					if (tickcounter == 1) {
						
						levelRects.add(blankBlockRect);
						
						//mapRects.add(new Rect((previousRectLeft + previousRectWidth), previousRectTop, (previousRectRight + blankBlockWidth), (int) (previousRectTop + (20 * dpscale))));
						mapRects.add(new Rect(previousRectRight, previousRectTop, (previousRectRight + blankBlockWidth), (int) (previousRectTop + (20 * dpscale))));

						
					} else {
						
						levelRects.add(levelRectsArray.get(randNum));
											
						//mapRects.add(new Rect((previousRectLeft + previousRectWidth) , previousRectTop, (previousRectRight + levelRects.get(x).width()), (previousRectTop + levelRects.get(x).height())));
						mapRects.add(new Rect(previousRectRight , previousRectTop, (previousRectRight + levelRectsArray.get(randNum).width()), (previousRectTop + levelRectsArray.get(randNum).height())));

						
						
					}
																			
						//previousRectLeft = (previousRectLeft + previousRectWidth);
						previousRectLeft = previousRectRight;
						previousRectRight = mapRects.get(x).right;
						
						if (tickcounter == 1) {
						
							previousRectWidth = blankBlockWidth;
							
							tickcounter = 0;
						} else {
							
							previousRectWidth = mapRects.get(x).width();
							tickcounter = 1;
						}
						
						previousRectTop = mapRects.get(x).top;
						
				}
			
		}
		
	}
		
	//Update method
	public void update(Canvas canvas) {

				
		if (mapRects.get(0).right < (-50 * dpscale)) {
			
			for (int h = 0; h <= (totalBlocksBuffer - 1); h++ ) {
			
				//Move all the mapRects down by 1
				Collections.swap(mapRects, h+1, h);
				//Move all the bitmaps down by 1
				Collections.swap(levelRects, h+1, h);
			
			}

			
		
			//Set the random number range to reflect how many bitmap blocks there are to use
			randMax = (mapBlocksNum - 1);
			//Generate a random number
			randNum = rand.nextInt((randMax+1) - 0) + 0;
			//Generate another random number
			//blankBlockRand = rand.nextInt(2);
			blankBlockRand = rand.nextInt(100);
			//Since everything has been moved down, we can tack a another block onto the end of the array lists
			
			randomHeightOffset = rand.nextInt(((maxHeightOffset + 1) + 1) - 0) + 0;
			
			//if (blankBlockRand == 1) {
			if (blankBlockRand > 50) {
				
				randomHeightOffset = 0 - randomHeightOffset;
				
			}
			
			
			if (tickOver == 1) {
			
				//Add another bitmap
				levelRects.add(blankBlockRect);
				//another rect too
				mapRects.add(new Rect((mapRects.get(totalBlocksBuffer - 1).left + mapRects.get(totalBlocksBuffer - 1).width()) , ((mapRects.get(totalBlocksBuffer - 1).top)), (mapRects.get(totalBlocksBuffer - 1).right + blankBlockWidth), (mapRects.get(totalBlocksBuffer - 1).bottom)));
				
				tickOver = 0;

			} else {
				//Add another bitmap
				levelRects.add(levelRectsArray.get(randNum));
				//another rect too
				mapRects.add(new Rect((mapRects.get(totalBlocksBuffer - 1).left + mapRects.get(totalBlocksBuffer - 1).width()) , (mapRects.get(totalBlocksBuffer - 1).top + randomHeightOffset), (levelRectsArray.get(randNum).width() + mapRects.get(totalBlocksBuffer - 1).right), (mapRects.get(totalBlocksBuffer - 1).top + levelRects.get(totalBlocksBuffer).height() + randomHeightOffset)));
				tickOver = 1;
			}	
			
			totalBlocksBuffer++;
			
		}
		
		
		
	}

	
	public void drawBackGround(Canvas canvas) {
	
		spaceBackground.draw();

		
	}
	
	public boolean getDropStatus() {
		return dropStatus;
	}

	public void draw(Canvas canvas) {


    	drawBackGround(canvas); //Draw background
    		
    	lolpaint.setColor(Color.WHITE);
    	
    	for (int z = 0; z <= totalBlocksBuffer; z ++) {
    		 
    		//canvas.drawBitmap(mapBitmaps.get(z), null, mapRects.get(z), null);
    		if (levelRects.get(z).width() > 10) {
    			canvas.drawRect(mapRects.get(z), platformPaint);
    		}
    	}	
    }
	
	
	//Getters and Setters	
	public void setHeight(int height) {
		levelHeight = height;
	}
	
	public int getHeight() {
		return levelHeight;
	}

	public void setWidth(int width) {
		levelWidth = width;
	}

	public int getWidth() {
		return levelWidth;
	}
	
	public void incrementScrollSpeed(int sPeed) {
		scrollSpeed += sPeed;
	}

	public int getScrollSpeed() {
		return scrollSpeed;
	}
	
	public void setYOffset(int yOff) {
		
		yOffset = yOff;
		
	}
	
	public int getYOffset() {
		
		return yOffset;
	}
	
	public int getBlocksBuffer() {
		
		return totalBlocksBuffer;

	}

	public void setDropStatus(boolean b) {
		
		dropStatus = b;
		
	}

	public void increaseHeightChange() {
		
		heightChangeProp += 1;
		
	}
	
	public void increaseBlankBlockWidth() {
		
		blankBlockWidth += 20;
		
	}
	
	public int getBlankBlockWidth() {
		
		return blankBlockWidth;
		
	}
	

	
}
