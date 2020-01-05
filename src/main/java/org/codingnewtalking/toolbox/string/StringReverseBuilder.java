package org.codingnewtalking.toolbox.string;

/**
 * @author lixinjie
 * @since 2020-01-05
 */
@SuppressWarnings("serial")
public class StringReverseBuilder
	extends AbstractStringReverseBuilder
	implements java.io.Serializable, CharSequence {

	public StringReverseBuilder() {
	    super(16);
	}

    public StringReverseBuilder(int capacity) {
        super(capacity);
    }

    public StringReverseBuilder(String str) {
        super(str.length() + 16);
        prepend(str);
    }

    public StringReverseBuilder(CharSequence seq) {
        this(seq.length() + 16);
        prepend(seq);
    }

    @Override
    public StringReverseBuilder prepend(Object obj) {
        return prepend(String.valueOf(obj));
    }

    @Override
    public StringReverseBuilder prepend(String str) {
        super.prepend(str);
        return this;
    }

    public StringReverseBuilder prepend(StringBuffer sb) {
        super.prepend(sb);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(CharSequence s) {
        super.prepend(s);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(CharSequence s, int start, int end) {
        super.prepend(s, start, end);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(char[] str) {
        super.prepend(str);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(char[] str, int offset, int len) {
        super.prepend(str, offset, len);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(boolean b) {
        super.prepend(b);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(char c) {
        super.prepend(c);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(int i) {
        super.prepend(i);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(long lng) {
        super.prepend(lng);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(float f) {
        super.prepend(f);
        return this;
    }

    @Override
    public StringReverseBuilder prepend(double d) {
        super.prepend(d);
        return this;
    }

    @Override
    public StringReverseBuilder delete(int start, int end) {
        super.delete(start, end);
        return this;
    }

    @Override
    public StringReverseBuilder deleteCharAt(int index) {
        super.deleteCharAt(index);
        return this;
    }

    @Override
    public StringReverseBuilder replace(int start, int end, String str) {
        super.replace(start, end, str);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int index, char[] str, int offset,
                                int len)
    {
        super.insert(index, str, offset, len);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, Object obj) {
            super.insert(offset, obj);
            return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, String str) {
        super.insert(offset, str);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, char[] str) {
        super.insert(offset, str);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int dstOffset, CharSequence s) {
            super.insert(dstOffset, s);
            return this;
    }

    @Override
    public StringReverseBuilder insert(int dstOffset, CharSequence s,
                                int start, int end)
    {
        super.insert(dstOffset, s, start, end);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, boolean b) {
        super.insert(offset, b);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, char c) {
        super.insert(offset, c);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, int i) {
        super.insert(offset, i);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, long l) {
        super.insert(offset, l);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, float f) {
        super.insert(offset, f);
        return this;
    }

    @Override
    public StringReverseBuilder insert(int offset, double d) {
        super.insert(offset, d);
        return this;
    }

    @Override
    public int indexOf(String str) {
        return super.indexOf(str);
    }

    @Override
    public int indexOf(String str, int fromIndex) {
        return super.indexOf(str, fromIndex);
    }

    @Override
    public int lastIndexOf(String str) {
        return super.lastIndexOf(str);
    }

    @Override
    public int lastIndexOf(String str, int fromIndex) {
        return super.lastIndexOf(str, fromIndex);
    }

    @Override
    public StringReverseBuilder reverse() {
        super.reverse();
        return this;
    }

    @Override
    public String toString() {
        // Create a copy, don't share the array
        return new String(value, value.length - count, count);
    }

    /**
     * Save the state of the {@code StringReverseBuilder} instance to a stream
     * (that is, serialize it).
     *
     * @serialData the number of characters currently stored in the string
     *             builder ({@code int}), followed by the characters in the
     *             string builder ({@code char[]}).   The length of the
     *             {@code char} array may be greater than the number of
     *             characters currently stored in the string builder, in which
     *             case extra characters are ignored.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(count);
        s.writeObject(value);
    }

    /**
     * readObject is called to restore the state of the StringBuffer from
     * a stream.
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        count = s.readInt();
        value = (char[]) s.readObject();
    }

}
