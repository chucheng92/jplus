package com.ryan.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Static utility methods to encode and decode UTF-8 {@code String}s.
 *
 * @author Osman KOCAK
 */
public final class UTF8
{
	/**
	 * Returns whether the given {@code String} can be encoded into UTF-8.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return whether {@code str} can be encoded into UTF-8.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static boolean canEncode(String str)
	{
		return Charsets.UTF_8.newEncoder().canEncode(str);
	}

	/**
	 * Returns whether the specified range in the given {@code String} can
	 * be encoded into UTF-8.
	 *
	 * @param str the input {@code String}.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to test.
	 *
	 * @return whether the specified range in {@code str} can be encoded
	 *	into UTF-8.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code str}'s
	 *	length.
	 */
	public static boolean canEncode(String str, int off, int len)
	{
		return canEncode(str.substring(off, off + len));
	}

	/**
	 * Returns whether the given byte array represents valid UTF-8 encoded
	 * characters.
	 *
	 * @param input the bytes to test.
	 *
	 * @return whether {@code input} repesents UTF-8 encoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	public static boolean canDecode(byte... input)
	{
		return canDecode(input, 0, input.length);
	}

	/**
	 * Returns whether the specified range in the given byte array represents
	 * valid UTF-8 encoded characters.
	 *
	 * @param input the input buffer.
	 * @param off the start index, inclusive.
	 * @param len the number of bytes to test.
	 *
	 * @return whether the specified range in {@code input} represents UTF-8
	 *	encoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code input}'s
	 *	length.
	 */
	public static boolean canDecode(byte[] input, int off, int len)
	{
		try {
			decode(input, off, len);
		} catch (IllegalArgumentException ex) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the UTF-8 encoding of the given {@code String}.
	 *
	 * @param str the {@code String} to encode.
	 *
	 * @return the UTF-8 encoding of the given {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IllegalArgumentException if {@code str} can't be encoded into
	 *	UTF-8.
	 */
	public static byte[] encode(String str)
	{
		CharsetEncoder encoder = Charsets.UTF_8.newEncoder();
		try {
			ByteBuffer out = encoder.encode(CharBuffer.wrap(str));
			byte[] bytes = new byte[out.limit()];
			out.get(bytes);
			return bytes;
		} catch (CharacterCodingException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	/**
	 * Returns the UTF-8 encoding of the specified character sequence.
	 *
	 * @param str the input {@code String}.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to encode.
	 *
	 * @return the UTF-8 encoding of the specified characters.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code str}'s
	 *	length.
	 * @throws IllegalArgumentException if {@code str} can't be encoded into
	 *	UTF-8.
	 */
	public static byte[] encode(String str, int off, int len)
	{
		return encode(str.substring(off, off + len));
	}

	/**
	 * Decodes the given UTF-8 encoded characters.
	 *
	 * @param input the UTF-8 encoded characters to decode.
	 *
	 * @return the decoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IllegalArgumentException if {@code input} doesn't represent
	 *	valid UTF-8 encoded characters.
	 */
	public static String decode(byte... input)
	{
		return decode(input, 0, input.length);
	}

	/**
	 * Decodes {@code len} UTF-8 encoded bytes from the given input buffer,
	 * starting at {@code off}.
	 *
	 * @param input the input buffer.
	 * @param off the starting offset.
	 * @param len the number of bytes to decode.
	 *
	 * @return the decoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code input}'s
	 *	length.
	 * @throws IllegalArgumentException if {@code input} doesn't represent
	 *	valid UTF-8 encoded characters.
	 */
	public static String decode(byte[] input, int off, int len)
	{
		CharsetDecoder decoder = Charsets.UTF_8.newDecoder();
		ByteBuffer buf = ByteBuffer.wrap(input, off, len);
		try {
			CharBuffer out = decoder.decode(buf);
			char[] chars = new char[out.limit()];
			out.get(chars);
			return new String(chars);
		} catch (CharacterCodingException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	private UTF8()
	{
		/* ... */
	}
}
