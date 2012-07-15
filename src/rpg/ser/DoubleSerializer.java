package rpg.ser;

public class DoubleSerializer extends Serializer<Double> {
  public static final DoubleSerializer singleton = new DoubleSerializer();

  private DoubleSerializer() {}

  @Override
  protected void serialize(Double x, ByteSink sink) {
    LongSerializer.singleton.serialize(Double.doubleToLongBits(x));
  }

  @Override
  protected Double deserialize(ByteSource source) {
    return Double.longBitsToDouble(LongSerializer.singleton.deserialize(source));
  }
}
