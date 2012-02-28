package com.obisapps.spacerun;

import java.util.Random;





import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {

	private Context context;
	
	MediaPlayer musicMPlayer;
	MediaPlayer jumpMPlayer;

	Random musicChoose = new Random();
	
	public SoundManager(Context aContext) {
		context = aContext;

	}
		
	public void init() {
				
		
		musicMPlayer = MediaPlayer.create(context, R.raw.calm);
		
		musicMPlayer.setLooping(true);
		
		jumpMPlayer = MediaPlayer.create(context, R.raw.jump);

				
	}
	
	
	
	public void jumpSound() {
		
		jumpMPlayer.start();
		
	}
	
	public void playMusic() {
		
		musicMPlayer.start();
		
	}
	
	public void stopMusic() {
		
		musicMPlayer.stop();
	}
	


	
}
