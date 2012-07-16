package rpg.serialization;

public class IntegerSerializer extends Serializer<Integer> {
  public static final IntegerSerializer singleton = new IntegerSerializer();

  private IntegerSerializer() {}

  @Override
  public void serialize(Integer n, ByteSink sink) {
    for (int i = 0; i < 4; ++i)
      sink.give((byte) (n >>> i * 8));
  }

  @Override
  public Integer deserialize(ByteSource source) {
    int n = 0;
    for (int i = 0; i < 4; ++i)
      n |= (((int) source.take()) & 0xFF) << i * 8;
    return n;
  }
}
