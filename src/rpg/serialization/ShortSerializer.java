package rpg.serialization;

public class ShortSerializer extends Serializer<Short> {
  public static final ShortSerializer singleton = new ShortSerializer();

  private ShortSerializer() {}

  @Override
  public void serialize(Short n, ByteSink sink) {
    for (int i = 0; i < 2; ++i)
      sink.give((byte) (n >>> i * 8));
  }

  @Override
  public Short deserialize(ByteSource source) {
    short n = 0;
    for (int i = 0; i < 2; ++i)
      n |= (((short) source.take()) & 0xFF) << i * 8;
    return n;
  }
}
