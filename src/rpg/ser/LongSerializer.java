package rpg.ser;

public class LongSerializer extends Serializer<Long> {
  public static final LongSerializer singleton = new LongSerializer();

  private LongSerializer() {}

  @Override
  public void serialize(Long n, ByteSink sink) {
    for (int i = 0; i < 8; ++i)
      sink.give((byte) (n >>> i * 8));
  }

  @Override
  public Long deserialize(ByteSource source) {
    long n = 0;
    for (int i = 0; i < 8; ++i)
      n |= (((long) source.take()) & 0xFF) << i * 8;
    return n;
  }
}
