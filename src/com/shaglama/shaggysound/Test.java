package com.shaglama.shaggysound;

import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.shaglama.shaggysound.mixer.Mixer;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, InterruptedException, IOException {
		// TODO Auto-generated method stub
		Mixer mixer;
		AudioFormat outFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,48000f,8,2,2,48000f,true);
		AudioFileFormat.Type[] types = AudioSystem.getAudioFileTypes();
		
		mixer = new Mixer(outFormat);
		
		mixer.addLoopingLine(mixer.getClass().getClassLoader().getResourceAsStream("sounds/fart.wav"),2);
		mixer.start();
		for(AudioFileFormat.Type t :types){
			System.out.println(t.toString());
		}
		Thread.sleep(3000);
		System.exit(0);
	}
	

}
