package org.codingnewtalking.toolbox.string;

import java.util.Arrays;

/**
 * @author lixinjie
 * @since 2020-01-05
 */
abstract class AbstractStringReverseBuilder implements CharSequence {

    char[] value;

    int count;

    AbstractStringReverseBuilder(int capacity) {
        value = new char[capacity];
    }

    @Override
    public int length() {
        return count;
    }

    public int capacity() {
        return value.length;
    }

    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > 0)
            ensureCapacityInternal(minimumCapacity);
    }

    private void ensureCapacityInternal(int minimumCapacity) {
        // overflow-conscious code
        if (minimumCapacity - value.length > 0) {
        	char[] newValue = new char[newCapacity(minimumCapacity)];
            System.arraycopy(value, value.length - count, newValue, newValue.length - count, count);
            value = newValue;
        }
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int newCapacity = (value.length << 1) + 2;
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0)
            ? hugeCapacity(minCapacity)
            : newCapacity;
    }

    private int hugeCapacity(int minCapacity) {
        if (Integer.MAX_VALUE - minCapacity < 0) { // overflow
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE)
            ? minCapacity : MAX_ARRAY_SIZE;
    }

    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOfRange(value, value.length - count, value.length);
        }
    }

    public void setLength(int newLength) {
        if (newLength < 0)
            throw new StringIndexOutOfBoundsException(newLength);
        ensureCapacityInternal(newLength);

        if (count < newLength) {
            Arrays.fill(value, value.length - newLength, value.length - count, '\0');
        }

        count = newLength;
    }

    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        return value[value.length - index - 1];
    }


    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        if (srcBegin < 0)
            throw new StringIndexOutOfBoundsException(srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
            throw new StringIndexOutOfBoundsException(srcEnd);
        if (srcBegin > srcEnd)
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, value.length - srcEnd, dst, dstBegin, srcEnd - srcBegin);
    }

    public void setCharAt(int index, char ch) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        value[value.length - index - 1] = ch;
    }

    public AbstractStringReverseBuilder prepend(Object obj) {
        return prepend(String.valueOf(obj));
    }

    public AbstractStringReverseBuilder prepend(String str) {
        if (str == null)
            return appendNull();
        int len = str.length();
        ensureCapacityInternal(count + len);
        str.getChars(0, len, value, value.length - count - len);
        count += len;
        return this;
    }

    // Documentation in subclasses because of synchro difference
    public AbstractStringReverseBuilder prepend(StringBuffer sb) {
        if (sb == null)
            return appendNull();
        int len = sb.length();
        ensureCapacityInternal(count + len);
        sb.getChars(0, len, value, value.length - count - len);
        count += len;
        return this;
    }

    /**
     * @since 1.8
     */
    AbstractStringReverseBuilder prepend(AbstractStringReverseBuilder asb) {
        if (asb == null)
            return appendNull();
        int len = asb.length();
        ensureCapacityInternal(count + len);
        asb.getChars(0, len, value, value.length - count - len);
        count += len;
        return this;
    }

    // Documentation in subclasses because of synchro difference
    public AbstractStringReverseBuilder prepend(CharSequence s) {
        if (s == null)
            return appendNull();
        if (s instanceof String)
            return this.prepend((String)s);
        if (s instanceof AbstractStringReverseBuilder)
            return this.prepend((AbstractStringReverseBuilder)s);

        return this.prepend(s, 0, s.length());
    }

    private AbstractStringReverseBuilder appendNull() {
        int c = value.length - count;
        ensureCapacityInternal(c + 4);
        final char[] value = this.value;
        value[c - 4] = 'n';
        value[c - 3] = 'u';
        value[c - 2] = 'l';
        value[c - 1] = 'l';
        count += 4;
        return this;
    }

    public AbstractStringReverseBuilder prepend(CharSequence s, int start, int end) {
        if (s == null)
            s = "null";
        if ((start < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                "start " + start + ", end " + end + ", s.length() "
                + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        for (int i = start, j = value.length - count - len; i < end; i++, j++)
            value[j] = s.charAt(i);
        count += len;
        return this;
    }

    public AbstractStringReverseBuilder prepend(char[] str) {
        int len = str.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(str, 0, value, value.length - count - len, len);
        count += len;
        return this;
    }

    public AbstractStringReverseBuilder prepend(char str[], int offset, int len) {
        if (len > 0)                // let arraycopy report AIOOBE for len < 0
            ensureCapacityInternal(count + len);
        System.arraycopy(str, offset, value, value.length - count - len, len);
        count += len;
        return this;
    }

    public AbstractStringReverseBuilder prepend(boolean b) {
    	int c = value.length - count;
        if (b) {
            ensureCapacityInternal(count + 4);
            value[c - 4] = 't';
            value[c - 3] = 'r';
            value[c - 2] = 'u';
            value[c - 1] = 'e';
            count += 4;
        } else {
            ensureCapacityInternal(count + 5);
            value[c - 5] = 'f';
            value[c - 4] = 'a';
            value[c - 3] = 'l';
            value[c - 2] = 's';
            value[c - 1] = 'e';
            count += 5;
        }
        return this;
    }

    public AbstractStringReverseBuilder prepend(char c) {
        ensureCapacityInternal(count + 1);
        value[value.length - count - 1] = c;
        count++;
        return this;
    }

    public AbstractStringReverseBuilder prepend(int i) {
        prepend(String.valueOf(i));
        return this;
    }

    public AbstractStringReverseBuilder prepend(long l) {
    	prepend(String.valueOf(l));
    	return this;
    }

    public AbstractStringReverseBuilder prepend(float f) {
        prepend(String.valueOf(f));
        return this;
    }

    public AbstractStringReverseBuilder prepend(double d) {
        prepend(String.valueOf(d));
        return this;
    }

    public AbstractStringReverseBuilder delete(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            end = count;
        if (start > end)
            throw new StringIndexOutOfBoundsException();
        int len = end - start;
        if (len > 0) {
            System.arraycopy(value, value.length - count, value, value.length - count + len, count - end);
            count -= len;
        }
        return this;
    }

    public AbstractStringReverseBuilder deleteCharAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        System.arraycopy(value, value.length - count, value, value.length - count + 1, count - index - 1);
        count--;
        return this;
    }

    public AbstractStringReverseBuilder replace(int start, int end, String str) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (start > count)
            throw new StringIndexOutOfBoundsException("start > length()");
        if (start > end)
            throw new StringIndexOutOfBoundsException("start > end");

        if (end > count)
            end = count;
        int len = str.length();
        int newCount = count + len - (end - start);
        ensureCapacityInternal(newCount);

        System.arraycopy(value, value.length - count, value, value.length - count + (end - start) - len, count - end);
        str.getChars(0, str.length(), value, value.length - start - len);
        count = newCount;
        return this;
    }

    public String substring(int start) {
        return substring(start, count);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }

    public String substring(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            throw new StringIndexOutOfBoundsException(end);
        if (start > end)
            throw new StringIndexOutOfBoundsException(end - start);
        return new String(value, value.length - end, end - start);
    }

    public AbstractStringReverseBuilder insert(int index, char[] str, int offset, int len) {
        if ((index < 0) || (index > length()))
            throw new StringIndexOutOfBoundsException(index);
        if ((offset < 0) || (len < 0) || (offset > str.length - len))
            throw new StringIndexOutOfBoundsException(
                "offset " + offset + ", len " + len + ", str.length "
                + str.length);
        ensureCapacityInternal(count + len);
        System.arraycopy(value, value.length - count, value, value.length - count - len, count - index);
        System.arraycopy(str, offset, value, value.length - index - len, len);
        count += len;
        return this;
    }

    public AbstractStringReverseBuilder insert(int offset, Object obj) {
        return insert(offset, String.valueOf(obj));
    }

    public AbstractStringReverseBuilder insert(int offset, String str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        if (str == null)
            str = "null";
        int len = str.length();
        ensureCapacityInternal(count + len);
        System.arraycopy(value, value.length - count, value, value.length - count - len, count - offset);
        str.getChars(0, str.length(), value, value.length - offset - len);
        count += len;
        return this;
    }
    public AbstractStringReverseBuilder insert(int offset, char[] str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        int len = str.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(value, value.length - count, value, value.length - count - len, count - offset);
        System.arraycopy(str, 0, value, value.length - offset - len, len);
        count += len;
        return this;
    }

    public AbstractStringReverseBuilder insert(int dstOffset, CharSequence s) {
        if (s == null)
            s = "null";
        if (s instanceof String)
            return this.insert(dstOffset, (String)s);
        return this.insert(dstOffset, s, 0, s.length());
    }

     public AbstractStringReverseBuilder insert(int dstOffset, CharSequence s,
                                         int start, int end) {
        if (s == null)
            s = "null";
        if ((dstOffset < 0) || (dstOffset > this.length()))
            throw new IndexOutOfBoundsException("dstOffset "+dstOffset);
        if ((start < 0) || (end < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                "start " + start + ", end " + end + ", s.length() "
                + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        System.arraycopy(value, value.length - count, value, value.length - count - len, count - dstOffset);
        int offset = value.length - dstOffset - len;
        for (int i = start; i < end; i++)
            value[offset++] = s.charAt(i);
        count += len;
        return this;
    }

    public AbstractStringReverseBuilder insert(int offset, boolean b) {
        return insert(offset, String.valueOf(b));
    }

    public AbstractStringReverseBuilder insert(int offset, char c) {
        ensureCapacityInternal(count + 1);
        System.arraycopy(value, value.length - count, value, value.length - count - 1, count - offset);
        value[value.length - offset - 1] = c;
        count += 1;
        return this;
    }

    public AbstractStringReverseBuilder insert(int offset, int i) {
        return insert(offset, String.valueOf(i));
    }

    public AbstractStringReverseBuilder insert(int offset, long l) {
        return insert(offset, String.valueOf(l));
    }

    public AbstractStringReverseBuilder insert(int offset, float f) {
        return insert(offset, String.valueOf(f));
    }

    public AbstractStringReverseBuilder insert(int offset, double d) {
        return insert(offset, String.valueOf(d));
    }

    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    public int indexOf(String str, int fromIndex) {
        return indexOf(value, 0, count, str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, count);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return lastIndexOf(value, 0, count, str, fromIndex);
    }

    public AbstractStringReverseBuilder reverse() {
        boolean hasSurrogates = false;
        int n = count - 1;
        for (int j = (n-1) >> 1; j >= 0; j--) {
            int k = n - j;
            char cj = value[j];
            char ck = value[k];
            value[j] = ck;
            value[k] = cj;
            if (Character.isSurrogate(cj) ||
                Character.isSurrogate(ck)) {
                hasSurrogates = true;
            }
        }
        if (hasSurrogates) {
            reverseAllValidSurrogatePairs();
        }
        return this;
    }

    /** Outlined helper method for reverse() */
    private void reverseAllValidSurrogatePairs() {
        for (int i = 0; i < count - 1; i++) {
            char c2 = value[i];
            if (Character.isLowSurrogate(c2)) {
                char c1 = value[i + 1];
                if (Character.isHighSurrogate(c1)) {
                    value[i++] = c1;
                    value[i] = c2;
                }
            }
        }
    }

    @Override
    public abstract String toString();

    final char[] getValue() {
        return value;
    }
    
    static int indexOf(char[] source, int sourceOffset, int sourceCount,
            String target, int fromIndex) {
        return indexOf(source, sourceOffset, sourceCount,
                       target.toCharArray(), 0, target.length(),
                       fromIndex);
    }
    
    static int indexOf(char[] source, int sourceOffset, int sourceCount,
            char[] target, int targetOffset, int targetCount,
            int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j]
                        == target[k]; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }
    
    static int lastIndexOf(char[] source, int sourceOffset, int sourceCount,
            String target, int fromIndex) {
        return lastIndexOf(source, sourceOffset, sourceCount,
                       target.toCharArray(), 0, target.length(),
                       fromIndex);
    }
    
    static int lastIndexOf(char[] source, int sourceOffset, int sourceCount,
            char[] target, int targetOffset, int targetCount,
            int fromIndex) {
        /*
         * Check arguments; return immediately where possible. For
         * consistency, don't check for null str.
         */
        int rightIndex = sourceCount - targetCount;
        if (fromIndex < 0) {
            return -1;
        }
        if (fromIndex > rightIndex) {
            fromIndex = rightIndex;
        }
        /* Empty string always matches. */
        if (targetCount == 0) {
            return fromIndex;
        }

        int strLastIndex = targetOffset + targetCount - 1;
        char strLastChar = target[strLastIndex];
        int min = sourceOffset + targetCount - 1;
        int i = min + fromIndex;

    startSearchForLastChar:
        while (true) {
            while (i >= min && source[i] != strLastChar) {
                i--;
            }
            if (i < min) {
                return -1;
            }
            int j = i - 1;
            int start = j - (targetCount - 1);
            int k = strLastIndex - 1;

            while (j > start) {
                if (source[j--] != target[k--]) {
                    i--;
                    continue startSearchForLastChar;
                }
            }
            return start - sourceOffset + 1;
        }
    }
}
