package net.consensys.pantheon.ethereum.jsonrpc.internal.results;

import net.consensys.pantheon.util.uint.UInt256;
import net.consensys.pantheon.util.uint.UInt256Value;

import java.math.BigInteger;
import java.util.Objects;

import com.google.common.base.Strings;

/**
 * Utility for formatting "quantity" fields and results to be returned. Quantity fields are
 * represented as minimal length hex strings with no zero-padding. There is one exception to this
 * rule: quantities equal to zero are represented by the hex string "0x0".
 */
public class Quantity {

  private static final String HEX_PREFIX = "0x";
  private static final String HEX_ZERO = "0x0";

  private Quantity() {}

  public static String create(final UInt256Value<?> value) {
    return uint256ToHex(value.asUInt256());
  }

  public static String create(final UInt256 value) {
    return uint256ToHex(value);
  }

  public static String create(final int value) {
    return uint256ToHex(UInt256.of(value));
  }

  public static String create(final long value) {
    return uint256ToHex(UInt256.of(value));
  }

  public static String format(final BigInteger input) {
    return formatMinimalValue(input.toString(16));
  }

  public static String format(final byte value) {
    return formatMinimalValue(Integer.toHexString(value));
  }

  public static String format(final int value) {
    return formatMinimalValue(Integer.toHexString(value));
  }

  /**
   * Fixed-length bytes sequences and should be returned as hex strings zero-padded to the expected
   * length.
   *
   * @param val the value to encode in the string
   * @param byteLength the number of bytes to be represented in the output string.
   * @return A zero-padded string containing byteLength * 2 characters, not including the 0x prefix
   */
  public static String longToPaddedHex(final long val, final int byteLength) {
    final String formatted = Long.toHexString(val);
    final String zeroPadding = Strings.repeat("0", byteLength * 2 - formatted.length());
    return String.format("%s%s%s", HEX_PREFIX, zeroPadding, formatted);
  }

  private static String uint256ToHex(final UInt256 value) {
    return value == null ? null : formatMinimalValue(value.toShortHexString());
  }

  private static String formatMinimalValue(final String hexValue) {
    final String prefixedHexString = prefixHexNotation(hexValue);
    return Objects.equals(prefixedHexString, HEX_PREFIX) ? HEX_ZERO : prefixedHexString;
  }

  private static String prefixHexNotation(final String hexValue) {
    return hexValue.startsWith(HEX_PREFIX) ? hexValue : HEX_PREFIX + hexValue;
  }
}