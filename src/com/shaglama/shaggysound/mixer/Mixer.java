package com.shaglama.shaggysound.mixer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.shaglama.shaggysound.converter.Converter;
import com.shaglama.shaggysound.mixer.lineIn.LineIn;
import com.shaglama.shaggysound.mixer.lineOut.LineOut;

public class Mixer implements Runnable {
	private static final AudioFormat DEFAULT_FORMAT = new AudioFormat(44100, 16, 2, true, false);
	private AudioFormat internalFormat;
	private ArrayList<LineIn> inputs = new ArrayList<LineIn>();
	private List<LineIn> addQueue = Collections.synchronizedList(new ArrayList<LineIn>());
	private List<LineIn> removeQueue = Collections.synchronizedList(new ArrayList<LineIn>());
	private ConcurrentHashMap<LineIn, Integer> loops = new ConcurrentHashMap<LineIn, Integer>();
	private SourceDataLine output;
	private AudioFormat format;
	private int internalFrameSize;
	private int externalFrameSize;
	private int numChannels;
	private int bytesPerChannel;
	private ByteOrder internalByteOrder;
	private ByteOrder outputByteOrder;
	private Thread thread;
	private boolean running = false;
	

	public Mixer() {
		this(DEFAULT_FORMAT);
	}

	public Mixer(AudioFormat format) {
		try {
			LineOut lineOut;
			this.format = format;
			internalFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, 2,4, format.getFrameRate(), true);
			internalFrameSize = internalFormat.getFrameSize();
			internalByteOrder = internalFormat.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
			outputByteOrder = format.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
			numChannels = format.getChannels();
			externalFrameSize = format.getFrameSize();
			bytesPerChannel = externalFrameSize / numChannels;
			lineOut = new LineOut(format);
			output = lineOut.line();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void run() {
		double outSampleL;
		double outSampleR;
		double outSampleMono;
		int numSamples;
		double max = Converter.FLOAT_32_MAX;
		ArrayList<LineIn> remove = new ArrayList<LineIn>();
		ByteBuffer outBuffer = ByteBuffer.allocate(externalFrameSize * 200);

		outBuffer.order(outputByteOrder);
		while (running) {
			synchronized (addQueue) {
				if (addQueue.size() > 0) {
					for (LineIn in : addQueue) {
						inputs.add(in);
						
					}
					addQueue.clear();
				}
			}
			synchronized (removeQueue) {
				if (removeQueue.size() > 0) {
					for (LineIn in : removeQueue) {
						inputs.remove(in);
					}
					removeQueue.clear();
				}
			}
			if (!outBuffer.hasRemaining()) {
				output.write(outBuffer.array(), 0, outBuffer.limit());
				outBuffer.clear();
			} else if (inputs.size() > 0) {
				outSampleL = 0;
				outSampleR = 0;
				outSampleMono = 0;
				numSamples = 0;
				for (LineIn is : inputs) {
					byte[] buffer = new byte[internalFrameSize];
					try {
						if (is.line().read(buffer, 0, internalFrameSize) != -1) {
							numSamples++;
							ByteBuffer sampleBuffer = ByteBuffer.allocate(internalFrameSize);
							sampleBuffer.order(internalByteOrder);
							for (int j = 0; j < buffer.length; j++) {
								sampleBuffer.put(buffer[j]);
							}
							sampleBuffer.rewind();
							outSampleL += Converter.toFloat32FromInt16Signed(sampleBuffer.getShort());
							outSampleR += Converter.toFloat32FromInt16Signed(sampleBuffer.getShort());

						} else {
							int numLoops;
							if (loops.containsKey(is)) {
								numLoops = loops.get(is);
								if (numLoops == 0) {
									remove.add(is);
									loops.remove(is);
								} else if (numLoops == -1) {
									is.reset();
								} else {
									is.reset();
									numLoops--;
									loops.put(is, numLoops);
								}
							} else {
								remove.add(is);
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (numSamples > 0) {
					if (numChannels == 1) {
						outSampleMono = outSampleL + outSampleR;
						outSampleMono = limit(outSampleMono, .8, max);
						outSampleMono = clip(outSampleMono, max);
					} else if (numChannels == 2) {
						outSampleL = limit(outSampleL, .8, max);
						outSampleL = clip(outSampleL, max);
						outSampleR = limit(outSampleL, .8, max);
						outSampleR = clip(outSampleL, max);
					} else {

					}
					if (format.getEncoding().equals(AudioFormat.Encoding.PCM_FLOAT)) {
						if (numChannels == 1) {
							outBuffer.putFloat((float) outSampleMono);
						} else {
							outBuffer.putFloat((float) outSampleL);
							outBuffer.putFloat((float) outSampleR);
						}
					} else if (format.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
						if (numChannels == 1) {
							outBuffer.put((byte) Converter.toInt8UnsignedFromFloat32((float) outSampleMono));
						} else {
							outBuffer.put((byte) Converter.toInt8UnsignedFromFloat32((float) outSampleL));
							outBuffer.put((byte) Converter.toInt8UnsignedFromFloat32((float) outSampleL));
						}
					} else if (format.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED)) {
						if (bytesPerChannel == 1) {
							if (numChannels == 1) {
								outBuffer.put(Converter.toInt8SignedFromFloat32((float) outSampleMono));
							} else {
								outBuffer.put(Converter.toInt8SignedFromFloat32((float) outSampleL));
								outBuffer.put(Converter.toInt8SignedFromFloat32((float) outSampleR));
							}
						} else if (bytesPerChannel == 2) {
							if (numChannels == 1) {
								outBuffer.putShort(Converter.toInt16SignedFromFloat32((float) outSampleMono));
							} else {
								outBuffer.putShort(Converter.toInt16SignedFromFloat32((float) outSampleL));
								outBuffer.putShort(Converter.toInt16SignedFromFloat32((float) outSampleR));
							}
						} else if (bytesPerChannel == 3) {
							if (numChannels == 1) {
								byte[] bytes = Converter.toBytesFromInt24Signed(
										Converter.toInt24SignedFromFloat32((float) outSampleMono),
										format.isBigEndian());
								for (byte b : bytes) {
									outBuffer.put(b);
								}
							} else {
								byte[] bytesL = Converter.toBytesFromInt24Signed(
										Converter.toInt24SignedFromFloat32((float) outSampleL), format.isBigEndian());
								byte[] bytesR = Converter.toBytesFromInt24Signed(
										Converter.toInt24SignedFromFloat32((float) outSampleR), format.isBigEndian());
								for (byte b : bytesL) {
									outBuffer.put(b);
								}
								for (byte b : bytesR) {
									outBuffer.put(b);
								}
							}
						} else if (bytesPerChannel == 4) {
							if (numChannels == 1) {
								outBuffer.putInt(Converter.toInt32SignedFromFloat32((float) outSampleMono));
							} else {
								outBuffer.putInt(Converter.toInt32SignedFromFloat32((float) outSampleMono));
								outBuffer.putInt(Converter.toInt32SignedFromFloat32((float) outSampleMono));
							}
						} else {

						}
					}
					outSampleL = 0;
					outSampleR = 0;
					outSampleMono = 0;
				}

				for (LineIn rm : remove) {
					inputs.remove(rm);
				}
			} else {
				int numBytes = outBuffer.position();
				if (numBytes > 0) {
					output.write(outBuffer.array(), 0, numBytes);
					outBuffer.clear();
				}
			}
		}
	}

	public synchronized void start() {
		if (!running) {
			running = true;
			output.start();
			thread = new Thread(this);
			thread.start();
		}

	}

	public synchronized void stop() {
		running = false;
		output.stop();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public LineIn addLine(InputStream inputStream) throws UnsupportedAudioFileException, IOException{
		LineIn lineIn = new LineIn(inputStream,internalFormat);
		addQueue.add(lineIn);
		return lineIn;
	}
	public LineIn addLine(File file) throws UnsupportedAudioFileException, IOException {
		LineIn lineIn = new LineIn(file, internalFormat);// format);
		addQueue.add(lineIn);
		return lineIn;
	}

	public LineIn addLine(String path) throws UnsupportedAudioFileException, IOException {
		return addLine(new File(path));
	}

	public boolean removeLine(LineIn lineIn) {

		return removeQueue.add(lineIn);
	}

	public LineIn addLoopingLine(InputStream inputStream, int numLoops) throws UnsupportedAudioFileException, IOException{
		LineIn in;
		if(numLoops >= -1){
			in = addLine(inputStream);
			loops.put(in, numLoops);
			return in;
		} else {
			return null;
		}
	}
	public LineIn addLoopingLine(File file, int numLoops) throws UnsupportedAudioFileException, IOException {
		
		LineIn in;
		if(numLoops >= -1){
			in = addLine(file);
			loops.put(in, numLoops);
			return in;
		} else {
			return null;
		}
	}

	public LineIn addLoopingLine(String path, int numLoops) throws UnsupportedAudioFileException, IOException {
		return addLoopingLine(new File(path), numLoops);
	}

	private LineIn[] getInputs() {
		LineIn[] inputs = new LineIn[this.inputs.size()];
		this.inputs.toArray(inputs);
		return inputs;
	}

	private double limit(double input, double limit, double max) {
		boolean negative = input < 0;
		double ratio;
		double limited;
		if (negative) {
			ratio = -input / max;
		} else {
			ratio = input / max;
		}
		limited = ratio * limit;
		ratio = limited / limit;
		if (ratio > .9) {
			double compressed;
			double compressionFactor = ratio / 20;// 0.115;
			double drop = (ratio - .9) * 10;
			compressed = limit * (ratio - (compressionFactor * drop));
			if (negative) {
				return -compressed;
			} else {
				return compressed;
			}
		} else {
			if (negative) {
				return -limited;
			} else {
				return limited;
			}
		}
	}

	private double clip(double input, double max) {
		double clipped;
		if(input > max){
			clipped = max;
		} else if(input < -max){
			clipped = -max;
		} else {
			clipped = input;
		}
		return clipped;
	}

}
