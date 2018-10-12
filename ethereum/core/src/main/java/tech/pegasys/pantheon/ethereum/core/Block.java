package net.consensys.pantheon.ethereum.core;

import net.consensys.pantheon.ethereum.rlp.RLP;
import net.consensys.pantheon.ethereum.rlp.RLPInput;
import net.consensys.pantheon.ethereum.rlp.RLPOutput;
import net.consensys.pantheon.util.bytes.BytesValue;

import java.util.List;
import java.util.Objects;

public class Block {

  private final BlockHeader header;
  private final BlockBody body;

  public Block(final BlockHeader header, final BlockBody body) {
    this.header = header;
    this.body = body;
  }

  public BlockHeader getHeader() {
    return header;
  }

  public BlockBody getBody() {
    return body;
  }

  public Hash getHash() {
    return header.getHash();
  }

  public BytesValue toRlp() {
    return RLP.encode(this::writeTo);
  }

  public int calculateSize() {
    return toRlp().size();
  }

  public void writeTo(final RLPOutput out) {
    out.startList();

    header.writeTo(out);
    out.writeList(body.getTransactions(), Transaction::writeTo);
    out.writeList(body.getOmmers(), BlockHeader::writeTo);

    out.endList();
  }

  public static Block readFrom(final RLPInput in, final BlockHashFunction hashFunction) {
    in.enterList();
    final BlockHeader header = BlockHeader.readFrom(in, hashFunction);
    final List<Transaction> transactions = in.readList(Transaction::readFrom);
    final List<BlockHeader> ommers = in.readList(rlp -> BlockHeader.readFrom(rlp, hashFunction));
    in.leaveList();

    return new Block(header, new BlockBody(transactions, ommers));
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Block)) {
      return false;
    }
    final Block other = (Block) obj;
    return header.equals(other.header) && body.equals(other.body);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, body);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Block{");
    sb.append("header=").append(header).append(", ");
    sb.append("body=").append(body);
    return sb.append("}").toString();
  }
}