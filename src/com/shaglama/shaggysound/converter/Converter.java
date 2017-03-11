package com.shaglama.shaggysound.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Converter {
	public static final int INT_8_UNSIGNED_MAX = 255;
	public static final int INT_8_UNSIGNED_MIN = 0;
	public static final int INT_8_SIGNED_MAX = Byte.MAX_VALUE;
	public static final int INT_8_SIGNED_MIN = -INT_8_SIGNED_MAX;
	public static final int INT_16_SIGNED_MAX = Short.MAX_VALUE;
	public static final int INT_16_SIGNED_MIN = -INT_16_SIGNED_MAX;
	public static final int INT_24_SIGNED_MAX = (int) ((Math.pow(2, 24) / 2) - 1);
	public static final int INT_24_SIGNED_MIN = -INT_24_SIGNED_MAX;
	public static final int INT_32_SIGNED_MAX = Integer.MAX_VALUE;
	public static final int INT_32_SIGNED_MIN = -INT_32_SIGNED_MAX;
	public static final long INT_64_SIGNED_MAX = Long.MAX_VALUE;
	public static final long INT_64_SIGNED_MIN = -INT_64_SIGNED_MAX;
	public static final float FLOAT_32_MAX = 1;
	public static final float FLOAT_32_MIN = -FLOAT_32_MAX;
	public static final double FLOAT_64_MAX = 1;
	public static final double FLOAT_64_MIN = -FLOAT_64_MAX;
	private static final float FLOAT_TYPE_MAX = Float.MAX_VALUE;
	private static final float FLOAT_TYPE_MIN = -FLOAT_TYPE_MAX;
	private static final double DOUBLE_TYPE_MAX = Double.MAX_VALUE;
	private static final double DOUBLE_TYPE_MIN = -DOUBLE_TYPE_MAX;

	public Converter() {
		// TODO Auto-generated constructor stub
	}

	public static float toFloat32FromInt8Unsigned(int input) {
		if (isInt8Unsigned(input)) {
			return input / (float) INT_8_UNSIGNED_MAX;
		} else {
			throw new IllegalArgumentException("invalid value for input. must be in 8 bit unsigned range (0-255)");
		}
	}

	public static float toFloat32FromInt8Signed(byte input) {
		return input / (float) INT_8_SIGNED_MAX;
	}

	public static float toFloat32FromInt16Signed(short input) {
		return input / (float) INT_16_SIGNED_MAX;
	}

	public static float toFloat32FromInt24Signed(int input) {
		if (isInt24Signed(input)) {
			return input / (float) INT_24_SIGNED_MAX;
		} else {
			throw new IllegalArgumentException("invalid value for input. must be in 24 bit range");
		}
	}

	public static float toFloat32FromInt32Signed(int input) {
		return input / (float) INT_32_SIGNED_MAX;
	}

	public static float toFloat32FromInt64Signed(long input) {
		return (float) ((double) input / INT_64_SIGNED_MAX);
	}

	public static float toFloat32(float input) {
		return (float) (input / FLOAT_TYPE_MAX);

	}

	public static float toFloat32(double input) {
		return (float) (input / DOUBLE_TYPE_MAX);
	}

	public static int toInt32SignedFromInt8Unsigned(int input) {
		return toInt32SignedFromFloat32(toFloat32FromInt8Unsigned(input));
	}

	public static int toInt32SignedFromInt8Signed(byte input) {
		return toInt32SignedFromFloat32(toFloat32FromInt8Signed(input));
	}

	public static int toInt32SignedFromInt16Signed(short input) {
		return toInt32SignedFromFloat32(toFloat32FromInt16Signed(input));
	}

	public static int toInt32SignedFromInt24Signed(int input) {
		return toInt32SignedFromFloat32(toFloat32FromInt24Signed(input));
	}

	public static int toInt32SignedFromInt64Signed(long input) {
		return toInt32SignedFromFloat32(toFloat32FromInt64Signed(input));
	}

	public static int toInt32SignedFromFloat32(float input) {
		if (isFloat32(input)) {
			return (short) (input * INT_32_SIGNED_MAX);
		} else {
			throw new IllegalArgumentException("invalid value for input. Must be in Float32 range (-1 : 1)");
		}
	}

	public static int toInt32Signed(float input) {
		return toInt32SignedFromFloat32(toFloat32(input));
	}

	public static int toInt32Signed(double input) {
		return toInt32SignedFromFloat32(toFloat32(input));
	}

	public static int toInt24SignedFromInt8Unsigned(int input) {
		return toInt24SignedFromFloat32(toFloat32FromInt8Unsigned(input));
	}

	public static int toInt24SignedFromInt8Signed(byte input) {
		return toInt24SignedFromFloat32(toFloat32FromInt8Signed(input));
	}

	public static int toInt24SignedFromInt16Signed(short input) {
		return toInt24SignedFromFloat32(toFloat32FromInt16Signed(input));
	}

	public static int toInt24SignedFromInt32Signed(int input) {
		return toInt24SignedFromFloat32(toFloat32FromInt32Signed(input));
	}

	public static int toInt24SignedFromInt64Signed(long input) {
		return toInt24SignedFromFloat32(toFloat32FromInt64Signed(input));
	}

	public static int toInt24SignedFromFloat32(float input) {
		if (isFloat32(input)) {
			return (int) (input * INT_24_SIGNED_MAX);
		} else {
			throw new IllegalArgumentException("invalid value for input. Must be in Float32 range (-1 : 1)");
		}
	}

	public static int toInt24Signed(float input) {
		return toInt24SignedFromFloat32(toFloat32(input));
	}

	public static int toInt24Signed(double input) {
		return toInt24SignedFromFloat32(toFloat32(input));
	}

	public static short toInt16SignedFromInt8Unsigned(int input) {
		return toInt16SignedFromFloat32(toFloat32FromInt8Unsigned(input));
	}

	public static short toInt16SignedFromInt8Signed(byte input) {
		return toInt16SignedFromFloat32(toFloat32FromInt8Signed(input));
	}

	public static short toInt16SignedFromInt24Signed(int input) {
		return toInt16SignedFromFloat32(toFloat32FromInt24Signed(input));
	}

	public static short toInt16SignedFromInt32Signed(int input) {
		return toInt16SignedFromFloat32(toFloat32FromInt32Signed(input));
	}

	public static short toInt16SignedFromInt64Signed(long input) {
		return toInt16SignedFromFloat32(toFloat32FromInt64Signed(input));
	}

	public static short toInt16SignedFromFloat32(float input) {
		if (isFloat32(input)) {
			return (short) (input * (float) INT_16_SIGNED_MAX);
		} else {
			throw new IllegalArgumentException("invalid value for input. Must be in Float32 range (-1 : 1)");
		}
	}

	public static short toInt16Signed(float input) {
		return toInt16SignedFromFloat32(toFloat32(input));
	}

	public static short toInt16Signed(double input) {
		return toInt16SignedFromFloat32(toFloat32(input));
	}

	public static byte toInt8SignedFromInt8Unsigned(int input) {
		return toInt8SignedFromFloat32(toFloat32FromInt8Unsigned(input));
	}

	public static byte toInt8SignedFromInt16Signed(short input) {
		return toInt8SignedFromFloat32(toFloat32FromInt16Signed(input));
	}

	public static byte toInt8SignedFromInt24Signed(int input) {
		return toInt8SignedFromFloat32(toFloat32FromInt24Signed(input));
	}

	public static byte toInt8SignedFromInt32Signed(int input) {
		return toInt8SignedFromFloat32(toFloat32FromInt32Signed(input));
	}

	public static byte toInt8SignedFromInt64Signed(long input) {
		return toInt8SignedFromFloat32(toFloat32FromInt64Signed(input));
	}

	public static byte toInt8SignedFromFloat32(float input) {
		if (isFloat32(input)) {
			return (byte) (input * INT_8_SIGNED_MAX);
		} else {
			throw new IllegalArgumentException("invalid value for input: " + input + ". Must be in Float32 range (-1 : 1)");
		}
	}

	public static byte toInt8Signed(float input) {
		return toInt8SignedFromFloat32(toFloat32(input));
	}

	public static int toInt8Signed(double input) {
		return toInt8SignedFromFloat32(toFloat32(input));
	}

	public static int toInt8UnsignedFromInt8Signed(byte input) {
		return toInt8UnsignedFromFloat32(toFloat32FromInt8Signed(input));
	}

	public static int toInt8UnsignedFromInt16Signed(short input) {
		return toInt8UnsignedFromFloat32(toFloat32FromInt16Signed(input));
	}

	public static int toInt8UnsignedFromInt24Signed(int input) {
		return toInt8UnsignedFromFloat32(toFloat32FromInt24Signed(input));

	}

	public static int toInt8UnsignedFromInt32Signed(int input) {
		return toInt8UnsignedFromFloat32(toFloat32FromInt32Signed(input));
	}

	public static int toInt8UnsignedFromInt64Signed(long input) {
		return toInt8UnsignedFromFloat32(toFloat32FromInt64Signed(input));
	}

	public static int toInt8UnsignedFromFloat32(float input) {
		if (isFloat32(input)) {
			return (int) (input * INT_8_UNSIGNED_MAX);
		} else {
			throw new IllegalArgumentException("invalid value for input. Must be in Float32 range (-1 : 1)");
		}
	}

	public static int toInt8Unsigned(float input) {
		return toInt8UnsignedFromFloat32(toFloat32(input));
	}

	public static int toInt8Unsigned(double input) {
		return toInt8UnsignedFromFloat32(toFloat32(input));
	}

	public static byte[] toBytesFromInt16Signed(short input, boolean bigEndian) {
		byte msb = (byte) (input >> 8);
		byte lsb = (byte) input;
		byte[] output = new byte[2];
		if (bigEndian) {
			output[0] = msb;
			output[1] = lsb;
		} else {
			output[0] = lsb;
			output[1] = msb;
		}
		return output;
	}

	public static byte[] toBytesFromInt24Signed(int input, boolean bigEndian) {
		int numBytes = 3;
		int shift = 8;
		byte[] in = new byte[numBytes];
		byte[] output = new byte[numBytes];
		if (isInt24Signed(input)) {
			for (int i = numBytes - 1; i >= 0; i--) {
				in[i] = (byte) (input >> (shift * i));
			}
			if (bigEndian) {
				output = in;
			} else {
				output = new byte[numBytes];
				int index = 0;
				for (int j = numBytes - 1; j >= 0; j--) {
					output[index] = in[j];
					index++;
				}
			}
		} else {
			throw new IllegalArgumentException("input outside of 24 bit range");
		}
		return output;
	}

	public static byte[] toBytesFromInt32Signed(int input, boolean bigEndian) {
		int numBytes = 4;
		int shift = 8;
		byte[] in = new byte[numBytes];
		byte[] output = new byte[numBytes];

		for (int i = numBytes - 1; i >= 0; i--) {
			in[i] = (byte) (input >> (shift * i));
		}
		if (bigEndian) {
			output = in;
		} else {
			output = new byte[numBytes];
			int index = 0;
			for (int j = numBytes - 1; j >= 0; j--) {
				output[index] = in[j];
				index++;
			}
		}
		return output;
	}

	public static byte[] toBytesFromInt64Signed(long input, boolean bigEndian) {
		int numBytes = 8;
		int shift = 8;
		byte[] in = new byte[numBytes];
		byte[] output = new byte[numBytes];

		for (int i = numBytes - 1; i >= 0; i--) {
			in[i] = (byte) (input >> (shift * i));
		}
		if (bigEndian) {
			output = in;
		} else {
			output = new byte[numBytes];
			int index = 0;
			for (int j = numBytes - 1; j >= 0; j--) {
				output[index] = in[j];
				index++;
			}
		}
		return output;
	}

	public static byte[] toByesFromFloat32(float input, boolean bigEndian) {
		if (isFloat32(input)) {
			return toBytes(input, bigEndian);
		} else {
			throw new IllegalArgumentException("invalid value for input. Must be in Float32 range ( -1 : 1 )");
		}
	}

	public static byte[] toByes(float input, boolean bigEndian) {
		int numBytes = 4;
		int shift = 8;
		int tmp;
		byte[] in = new byte[numBytes];
		byte[] output = new byte[numBytes];

		tmp = Float.floatToIntBits(input);
		for (int i = numBytes - 1; i >= 0; i--) {
			in[i] = (byte) (tmp >> (shift * i));
		}
		if (bigEndian) {
			output = in;
		} else {
			output = new byte[numBytes];
			int index = 0;
			for (int j = numBytes - 1; j >= 0; j--) {
				output[index] = in[j];
				index++;
			}
		}
		return output;
	}

	public static byte[] toBytes(double input, boolean bigEndian) {
		int numBytes = 8;
		int shift = 8;
		long tmp;
		byte[] in = new byte[numBytes];
		byte[] output = new byte[numBytes];

		tmp = Double.doubleToLongBits(input);
		for (int i = numBytes - 1; i >= 0; i--) {
			in[i] = (byte) (tmp >> (shift * i));
		}
		if (bigEndian) {
			output = in;
		} else {
			output = new byte[numBytes];
			int index = 0;
			for (int j = numBytes - 1; j >= 0; j--) {
				output[index] = in[j];
				index++;
			}
		}
		return output;
	}

	public static boolean isInt8Unsigned(int input) {
		if (input >= 0 && input <= 255) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInt8Signed(int input) {
		if (input <= INT_8_SIGNED_MAX && input >= INT_8_SIGNED_MIN) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInt16Signed(int input) {
		if (input <= INT_16_SIGNED_MAX && input >= INT_16_SIGNED_MAX) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInt24Signed(int input) {
		if (input <= INT_24_SIGNED_MAX && input >= INT_24_SIGNED_MIN) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInt32Signed(double input) {
		if (input <= INT_32_SIGNED_MAX && input >= INT_32_SIGNED_MIN) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isFloat32(float input) {
		if (input <= 1 && input >= -1) {
			return true;
		} else {
			return false;
		}
	}

}
