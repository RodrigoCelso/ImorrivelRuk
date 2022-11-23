package com.ufmt.world;

import java.io.*;
import javax.sound.sampled.*;

public class Sound {
	
	public static class Clips{
		public Clip[] clips;
		private int count;
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
			if(buffer == null) return;
			
			this.count = count;
			clips = new Clip[count];
			
			for(int i = 0; i < count; i++){
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
		}
		
		public void play(){
			if(clips == null) return;
			clips[0].stop();
			clips[0].setFramePosition(0);
			clips[0].start();
		}
		
		public void loop(){
			if(clips == null) return;
			clips[0].loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		public void stop(){
			if(clips == null) return;
			clips[0].stop();
		}
	}	
	
	public static Clips punch = load("/punch.wav",1);
	
	private static Clips load(String name, int count){
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer,0,read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data,count);
		}catch(Exception e){
			try{
				return new Clips(null,0);
			}catch(Exception ee){
				return null;
			}
		}
	}
}
