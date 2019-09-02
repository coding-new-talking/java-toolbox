package org.codingnewtalking.toolbox.util;

/**
 * @author lixinjie
 * @since 2019-07-09
 */
public class ByteUtils {
	
	public static int toUnsigned(byte[] bytes, int offset, int length) {
		if (length == 1) {
			return toUnsigned(bytes[offset]);
		}
		if (length == 2) {
			return toUnsigned(bytes[offset], bytes[offset + 1]);
		}
		return -1;
	}
	
	public static long toUnsignedLong(byte[] bytes, int offset, int length) {
		if (length == 1 || length == 2) {
			return toUnsigned(bytes, offset, length);
		}
		if (length == 4) {
			return toUnsigned(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3]);
		}
		return -1;
	}
	
	public static int toSigned(byte[] bytes, int offset, int length) {
		if (length == 1) {
			return toSigned(bytes[offset]);
		}
		if (length == 2) {
			return toSigned(bytes[offset], bytes[offset + 1]);
		}
		if (length == 4) {
			return toSigned(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3]);
		}
		return -1;
	}
	
	public static long toSignedLong(byte[] bytes, int offset, int length) {
		if (length == 1 || length == 2 || length == 4) {
			return toSigned(bytes, offset, length);
		}
		if (length == 8) {
			return toSigned(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3],
					bytes[offset + 4], bytes[offset + 5], bytes[offset + 6], bytes[offset + 7]);
		}
		return -1;
	}
	
	public static short toShort(byte[] bytes, int offset, int length) {
		if (length == 2) {
			return toShort(bytes[offset], bytes[offset + 1]);
		}
		return -1;
	}
	
	public static int toInteger(byte[] bytes, int offset, int length) {
		if (length == 4) {
			return toInteger(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3]);
		}
		return -1;
	}
	
	public static long toLong(byte[] bytes, int offset, int length) {
		if (length == 8) {
			return toLong(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3],
					bytes[offset + 4], bytes[offset + 5], bytes[offset + 6], bytes[offset + 7]);
		}
		return -1;
	}
	
	public static float toFloat(byte[] bytes, int offset, int length) {
		if (length == 4) {
			return toFloat(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3]);
		}
		return -1;
	}
	
	public static double toDouble(byte[] bytes, int offset, int length) {
		if (length == 8) {
			return toDouble(bytes[offset], bytes[offset + 1], bytes[offset + 2], bytes[offset + 3],
					bytes[offset + 4], bytes[offset + 5], bytes[offset + 6], bytes[offset + 7]);
		}
		return -1;
	}

	public static int toUnsigned(byte b) {
		//把一个byte原样不变的放到int
		//类型的第4个字节位置，且前面
		//3个字节都清空为0
		return b & 0xff;
	}
	
	public static int toUnsigned(byte b1, byte b0) {
		return toUnsigned(b1) << 8 | toUnsigned(b0);
	}
	
	public static long toUnsigned(byte b3, byte b2, byte b1, byte b0) {
		return (long)toUnsigned(b3) << 24 | toUnsigned(b2) << 16 | toUnsigned(b1) << 8 | toUnsigned(b0);
	}
	
	public static int toSigned(byte b) {
		//把一个byte原样不变的放到int
		//类型的第4个字节位置，且前面
		//3个字节都填充为byte的符号位
		return b;
	}
	
	public static int toSigned(byte b1, byte b0) {
		return toSigned(b1) << 8 | toUnsigned(b0);
	}
	
	public static int toSigned(byte b3, byte b2, byte b1, byte b0) {
		return toSigned(b3) << 24 | toUnsigned(b2) << 16 | toUnsigned(b1) << 8 | toUnsigned(b0);
	}
	
	public static long toSigned(byte b7, byte b6, byte b5, byte b4, byte b3, byte b2, byte b1, byte b0) {
		return (long)toSigned(b7) << 56 | (long)toUnsigned(b6) << 48 | (long)toUnsigned(b5) << 40
				| (long)toUnsigned(b4) << 32
				| (long)toUnsigned(b3) << 24 | toUnsigned(b2) << 16 | toUnsigned(b1) << 8 | toUnsigned(b0);
	}
	
	public static short toShort(byte b1, byte b0) {
		return (short)toSigned(b1, b0);
	}
	
	public static int toInteger(byte b3, byte b2, byte b1, byte b0) {
		return toSigned(b3, b2, b1, b0);
	}
	
	public static long toLong(byte b7, byte b6, byte b5, byte b4, byte b3, byte b2, byte b1, byte b0) {
		return toSigned(b7, b6, b5, b4, b3, b2, b1, b0);
	}
	
	public static float toFloat(byte b3, byte b2, byte b1, byte b0) {
		return Float.intBitsToFloat(toSigned(b3, b2, b1, b0));
	}
	
	public static double toDouble(byte b7, byte b6, byte b5, byte b4, byte b3, byte b2, byte b1, byte b0) {
		return Double.longBitsToDouble(toSigned(b7, b6, b5, b4, b3, b2, b1, b0));
	}
	
	public static String toString(byte[] bytes, int offset, int length) {
		return new String(bytes, offset, length);
	}
	
}
