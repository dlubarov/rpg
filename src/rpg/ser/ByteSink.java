package rpg.ser;

public final class ByteSink {
  private static final int INITIAL_CAPACITY = 4;

  private byte[] data;
  private int pos = 0;

  public ByteSink() {
    data = new byte[INITIAL_CAPACITY];
  }

  private void expand() {
    byte[] newData = new byte[data.length * 2];
    System.arraycopy(data, 0, newData, 0, pos);
    data = newData;
  }

  public void give(byte b) {
    if (pos == data.length)
      expand();
    data[pos++] = b;
  }

  public void giveAll(byte[] bs) {
    // TODO: optimize (arraycopy)
    for (byte b : bs)
      give(b);
  }

  public byte[] getData() {
    byte[] result = new byte[pos];
    System.arraycopy(data, 0, result, 0, pos);
    return result;
  }
}
