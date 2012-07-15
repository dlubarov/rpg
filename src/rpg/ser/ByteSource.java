package rpg.ser;

public final class ByteSource {
  private final byte[] data;
  private int pos = 0;

  public ByteSource(byte[] data) {
    this.data = data;
  }

  public byte take() {
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
