package rpg.serialization;

public class DoubleSerializer extends Serializer<Double> {
  public static final DoubleSerializer singleton = new DoubleSerializer();

  private DoubleSerializer() {}

  @Override
  public void serialize(Double x, ByteSink sink) {
    LongSerializer.singleton.serialize(Double.doubleToLongBits(x), sink);
  }

  @Override
  public Double deserialize(ByteSource source) {
    return Double.longBitsToDouble(LongSerializer.singleton.deserialize(source));
  }
}
