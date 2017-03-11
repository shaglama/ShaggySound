package com.shaglama.shaggysound.mixer.lineIn;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.tritonus.share.sampled.AudioFileTypes;

public class LineIn {
	private AudioInputStream line;

	public LineIn(File file, AudioFormat targetFormat) throws UnsupportedAudioFileException, IOException {
		AudioInputStream lineIn = AudioSystem.getAudioInputStream(file);
		line = convert(lineIn, targetFormat);
		line.mark((int) (line.getFormat().getFrameSize() * line.getFrameLength()));

	}

	public LineIn(String path, AudioFormat targetFormat) throws UnsupportedAudioFileException, IOException {
		this(new File(path), targetFormat);
	}

	public LineIn(InputStream input, AudioFormat targetFormat) throws UnsupportedAudioFileException, IOException {
		InputStream buffer;
		AudioInputStream lineIn;
		buffer = new BufferedInputStream(input);
		lineIn = AudioSystem.getAudioInputStream(buffer);
		line = convert(lineIn, targetFormat);
		line.mark((int) (line.getFormat().getFrameSize() * line.getFrameLength()));
	}

	public AudioInputStream line() {
		return line;
	}

	public void reset() throws IOException {
		line.reset();
	}

	private AudioInputStream convert(AudioInputStream in, AudioFormat targetFormat)
			throws IOException, UnsupportedAudioFileException {
		if (in.getFormat().equals(targetFormat)) {
			return in;
		} else {
			byte[] bytes;
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			ByteArrayInputStream stream;
			AudioSystem.write(AudioSystem.getAudioInputStream(targetFormat, in), AudioFileTypes.AU, outStream);
			bytes = outStream.toByteArray();
			stream = new ByteArrayInputStream(bytes);
			return AudioSystem.getAudioInputStream(stream);
		}
	}

}
