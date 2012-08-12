package rpg.serialization;

public final class ByteSerializer extends Serializer<Byte> {
  public static final ByteSerializer singleton = new ByteSerializer();

  private ByteSerializer() {}

  @Override public void serialize(Byte b, ByteSink sink) {
    sink.give(b);
  }

  @Override public Byte deserialize(ByteSource source) {
    return source.take();
  }
}
