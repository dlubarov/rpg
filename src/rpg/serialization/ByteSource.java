package rpg.serialization;

public final class ByteSource {
  private final byte[] data;
  private int pos = 0;

  public ByteSource(byte[] data) {
    this.data = data;
  }

  public byte take() {
    assert pos < data.length : String.format("Exhausted source of length %d.", data.length);
    return data[pos++];
  }

  public byte[] takeN(int n) {
    byte[] result = new byte[n];
    System.arraycopy(data, pos, result, 0, n);
    pos += n;
    return result;
  }

  public boolean isEmpty() {
    return pos == data.length;
  }
}
