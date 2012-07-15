package rpg.ser;

public final class ByteSerializer extends Serializer<Byte> {
  public static final ByteSerializer singleton = new ByteSerializer();

  private ByteSerializer() {}

  @Override
  protected void serialize(Byte b, ByteSink sink) {
    sink.give(b);
  }

  @Override
  protected Byte deserialize(ByteSource source) {
    return source.take();
  }
}
