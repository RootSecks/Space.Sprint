package com.obisapps.spacerun;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;

public class PickUpManager {

	
	Context context;
	Canvas canvas;
	
	int pickupNum;
	
	Random randNum = new Random();
	
	
	public PickUpManager(Context pContext) {
	
		context = pContext;
		
		
	}

	
	public void init(Canvas pCanvas) {
		
		canvas = pCanvas;
		
		pickupNum = 0;
		
	}
	
	
	
	public void update() {
		
		/*
		if (pickupNum < 10) {
			
			createPickUp();
			
		}
		*/
		
		
	}
	
	
	public void draw() {
		
		
	}
	
	
	
	private void createPickUp() {
				
	}
	
	
	
	
	
}
