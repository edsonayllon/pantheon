package net.consensys.pantheon.ethereum.jsonrpc.internal.parameters;

import net.consensys.pantheon.ethereum.core.Address;
import net.consensys.pantheon.ethereum.core.Wei;
import net.consensys.pantheon.util.bytes.BytesValue;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

// Represents parameters for a eth_call or eth_estimateGas JSON-RPC methods.
public class CallParameter {

  private final Address from;

  private final Address to;

  private final long gasLimit;

  private final Wei gasPrice;

  private final Wei value;

  private final BytesValue payload;

  @JsonCreator
  public CallParameter(
      @JsonProperty("from") final String from,
      @JsonProperty("to") final String to,
      @JsonProperty("gas") final String gasLimit,
      @JsonProperty("gasPrice") final String gasPrice,
      @JsonProperty("value") final String value,
      @JsonProperty("data") final String payload) {
    this.from = from != null ? Address.fromHexString(from) : null;
    this.to = to != null ? Address.fromHexString(to) : null;
    this.gasLimit = gasLimit != null ? Long.decode(gasLimit) : -1;
    this.gasPrice = gasPrice != null ? Wei.fromHexString(gasPrice) : null;
    this.value = value != null ? Wei.fromHexString(value) : null;
    this.payload = payload != null ? BytesValue.fromHexString(payload) : null;
  }

  public Address getFrom() {
    return from;
  }

  public Address getTo() {
    return to;
  }

  public long getGasLimit() {
    return gasLimit;
  }

  public Wei getGasPrice() {
    return gasPrice;
  }

  public Wei getValue() {
    return value;
  }

  public BytesValue getPayload() {
    return payload;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CallParameter that = (CallParameter) o;
    return gasLimit == that.gasLimit
        && Objects.equal(from, that.from)
        && Objects.equal(to, that.to)
        && Objects.equal(gasPrice, that.gasPrice)
        && Objects.equal(value, that.value)
        && Objects.equal(payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(from, to, gasLimit, gasPrice, value, payload);
  }
}