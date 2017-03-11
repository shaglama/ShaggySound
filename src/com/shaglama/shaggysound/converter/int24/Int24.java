package com.shaglama.shaggysound.converter.int24;

import java.util.Comparator;

public class Int24 extends Number implements Comparator<Int24>{
	public static final int MAX_VALUE = 8388607;
	public static final int MIN_VALUE = -8388608;
	private Integer value;
	
	public Int24(Number value) throws IllegalArgumentException {
		set(value);
	}
	private void set(Number value){
		int intValue = value.intValue();
		if(intValue < MIN_VALUE || intValue > MAX_VALUE){
			throw new IllegalArgumentException("invalid value. Must be in the range ( " + MIN_VALUE + " : " + MAX_VALUE + " )");
		} else {
			this.value = intValue;
		}
	}
	@Override
	public double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public float floatValue() {
		return value.floatValue();
	}

	@Override
	public int intValue() {
		return value.intValue();
	}

	@Override
	public long longValue() {
		return value.longValue();
	}
	
	
	public Number add(Number addend){
		if(addend instanceof Byte){
			return new Integer(value.intValue() + addend.byteValue());
		} else if(addend instanceof Short){
			return new Integer(value.intValue() + addend.shortValue());
		} else if(addend instanceof Integer){
			return new Integer(value.intValue() + addend.intValue());
		} else if(addend instanceof Long){
			return new Long(value.longValue() + addend.longValue());
		} else if(addend instanceof Float){
			return new Float(value.floatValue() + addend.floatValue());
		} else if(addend instanceof Double){
			return new Double(value.doubleValue() + addend.doubleValue());
		} else if(addend instanceof Int24){
			return new Int24(value.intValue() + addend.intValue());
		} else {
			return new Integer(value.intValue() + addend.intValue());
		}
	}
	public Number subtract(Number subtrahend){
		if(subtrahend instanceof Byte){
			return new Integer(value.intValue() - subtrahend.byteValue());
		} else if(subtrahend instanceof Short){
			return new Integer(value.intValue() - subtrahend.shortValue());
		} else if(subtrahend instanceof Integer){
			return new Integer(value.intValue() - subtrahend.intValue());
		} else if(subtrahend instanceof Long){
			return new Long(value.longValue() - subtrahend.longValue());
		} else if(subtrahend instanceof Float){
			return new Float(value.floatValue() - subtrahend.floatValue());
		} else if(subtrahend instanceof Double){
			return new Double(value.doubleValue() - subtrahend.doubleValue());
		} else if(subtrahend instanceof Int24){
			return new Int24(value.intValue() - subtrahend.intValue());
		} else {
			return new Integer(value.intValue() - subtrahend.intValue());
		}
	}
	public Number multiply(Number minuend){
		if(minuend instanceof Byte){
			return new Integer(value.intValue() * minuend.byteValue());
		} else if(minuend instanceof Short){
			return new Integer(value.intValue() * minuend.shortValue());
		} else if(minuend instanceof Integer){
			return new Integer(value.intValue() * minuend.intValue());
		} else if(minuend instanceof Long){
			return new Long(value.longValue() * minuend.longValue());
		} else if(minuend instanceof Float){
			return new Float(value.floatValue() * minuend.floatValue());
		} else if(minuend instanceof Double){
			return new Double(value.doubleValue() * minuend.doubleValue());
		} else if(minuend instanceof Int24){
			return new Int24(value.intValue() * minuend.intValue());
		} else {
			return new Integer(value.intValue() * minuend.intValue());
		}
	}
	public Number divide(Number divisor){
		if(divisor instanceof Byte){
			return new Integer(value.intValue() / divisor.byteValue());
		} else if(divisor instanceof Short){
			return new Integer(value.intValue() / divisor.shortValue());
		} else if(divisor instanceof Integer){
			return new Integer(value.intValue() / divisor.intValue());
		} else if(divisor instanceof Long){
			return new Long(value.longValue() / divisor.longValue());
		} else if(divisor instanceof Float){
			return new Float(value.floatValue() / divisor.floatValue());
		} else if(divisor instanceof Double){
			return new Double(value.doubleValue() / divisor.doubleValue());
		} else if(divisor instanceof Int24){
			return new Int24(value.intValue() / divisor.intValue());
		} else {
			return new Integer(value.intValue() / divisor.intValue());
		}
	}
	public Number modulus(Number operand){
		if(operand instanceof Byte){
			return new Integer(value.intValue() % operand.byteValue());
		} else if(operand instanceof Short){
			return new Integer(value.intValue() % operand.shortValue());
		} else if(operand instanceof Integer){
			return new Integer(value.intValue() % operand.intValue());
		} else if(operand instanceof Long){
			return new Long(value.longValue() % operand.longValue());
		} else if(operand instanceof Float){
			return new Float(value.floatValue() % operand.floatValue());
		} else if(operand instanceof Double){
			return new Double(value.doubleValue() % operand.doubleValue());
		} else if(operand instanceof Int24){
			return new Int24(value.intValue() % operand.intValue());
		} else {
			return new Integer(value.intValue() % operand.intValue());
		}
	}
	public int compareTo(Number operand){
		if(operand instanceof Byte){
			return Byte.compare(value.byteValue(),operand.byteValue());
		} else if(operand instanceof Short){
			return Short.compare(value.shortValue(), operand.shortValue());
		} else if(operand instanceof Integer){
			return Integer.compare(value.intValue(), operand.intValue());
		} else if(operand instanceof Long){
			return Long.compare(value.longValue(), operand.longValue());
		} else if(operand instanceof Float){
			return Float.compare(value.floatValue(), operand.floatValue());
		} else if(operand instanceof Double){
			return Double.compare(value.doubleValue(), operand.doubleValue());
		} else if(operand instanceof Int24){
			return value.compareTo(operand.intValue());
		} else {
			return value.compareTo(operand.intValue());
		}
	}
	@Override
	public int compare(Int24 o1, Int24 o2) {
		return o1.compareTo(o2);
	}
	public boolean equals(Number n){
		if(compareTo(n) == 0){
			return true;
		} else {
			return false;
		}
	}
	public double pow(double exponent){
		return Math.pow(value,exponent);
	}
	public double sqrt(){
		return Math.sqrt(value);
	}
	public String toString(){
		return value.toString();
	}
	public byte[] toBytes(boolean bigEndian){
		int numBytes = 3;
		int shift = 8;
		byte[] in = new byte[numBytes];
		byte[] output = new byte[numBytes];
		for (int i = numBytes - 1; i >= 0; i--) {
			in[i] = (byte) (value >> (shift * i));
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
	public static Int24 valueOf(Number n){
		return new Int24(n);
	}
	
	
}
