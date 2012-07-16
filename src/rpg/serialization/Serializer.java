package rpg.serialization;

public abstract class Serializer<T> {
  public abstract void serialize(T object, ByteSink sink);

  public abstract T deserialize(ByteSource source);

  public final byte[] serialize(T object) {
    ByteSink sink = new ByteSink();
    serialize(object, sink);
    return sink.getData();
  }

  public final T deserialize(byte[] data) {
    ByteSource source = new ByteSource(data);
    T result = deserialize(source);
    assert source.isEmpty();
    return result;
  }
}
