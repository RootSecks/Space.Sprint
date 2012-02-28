package com.obisapps.spacerun;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class SpaceBackground {

	private Canvas canvas;
	
	LevelCharacter levelCharacter;
	
	Paint speedParticlePaint = new Paint();
	Random randGen = new Random();
	
	List<Point> starList = new ArrayList<Point>();
	
	Paint starPaint = new Paint();
	
	
	public SpaceBackground() {

	
	}
	
	
	public void init(Canvas pCanvas, LevelCharacter lCharacter) {
		
		canvas = pCanvas;
		
		levelCharacter = lCharacter;
		
		starList.clear();
		
		for (int b = 0; b <= 20; b++ ) {
			
			starList.add(new Point(randGen.nextInt(canvas.getWidth()), randGen.nextInt(canvas.getHeight())));
			
			
		}
		
		
		starPaint.setColor(Color.YELLOW);
		
	}
	

	public void draw() {

		canvas.drawColor(Color.BLACK);
		
		
		for ( int d = 0; d <= (starList.size() - 1); d++ ) {
			
			if (starList.get(d) != null) {
				
				canvas.drawLine((starList.get(d).x - 4), starList.get(d).y, (starList.get(d).x + 4), starList.get(d).y, starPaint);
				canvas.drawLine(starList.get(d).x, (starList.get(d).y - 4), starList.get(d).x, (starList.get(d).y + 4), starPaint);
				
			}
		
		}
		
	}
	
	public void update() {
		if (!starList.isEmpty()) {
				
		
			for ( int y = 0; y <= (starList.size() - 1); y++ ) { 
				if (starList.get(y) != null) {
					//starList.get(y).x -= (levelCharacter.getScrollSpeed()/2);
					starList.get(y).x -= 1;

				}
			}

		
			int upcomingStars = 0;
			
			for ( int x = 0; x <= (starList.size() - 1); x++ ) {
				
				
				
				if (starList.get(x) != null) {
				
					if (starList.get(x).x < -50) {
					
						starList.remove(x);
						
					}
					
					if (starList.get(x).x > canvas.getWidth()) {
						
						upcomingStars ++;
						
					}
				
				}
				
			}
			
			if (upcomingStars < 5) {
				
				
				for (int b = 0; b <= 5; b++ ) {
					
					starList.add(new Point(randGen.nextInt(canvas.getWidth()) + canvas.getWidth(), randGen.nextInt(canvas.getHeight())));
					
					
				}
				
				
				
			}
			
		
		}
		
	}

	
	
	
}
