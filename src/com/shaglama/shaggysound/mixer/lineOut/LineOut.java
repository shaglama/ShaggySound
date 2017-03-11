package com.shaglama.shaggysound.mixer.lineOut;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class LineOut {
	private DataLine.Info info;
	private SourceDataLine line;
	
	public LineOut(AudioFormat format) throws LineUnavailableException {
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,format);
		line = (SourceDataLine)AudioSystem.getLine(info);
		line.open(format);
	}
	public SourceDataLine line(){
		return line;
	}
	public AudioFormat format(){
		return line.getFormat();
	}
	

}
