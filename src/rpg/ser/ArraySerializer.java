package rpg.ser;

public class ArraySerializer<T> extends Serializer<T[]> {
  private final Serializer<T> elementSerializer;

  public ArraySerializer(Serializer<T> elementSerializer) {
    this.elementSerializer = elementSerializer;
  }

  @Override
  public void serialize(T[] arr, ByteSink sink) {
    sink.giveAll(IntegerSerializer.singleton.serialize(arr.length));
    for (T elem : arr)
      sink.giveAll(elementSerializer.serialize(elem));
  }

  /**
   * Deserialize an array. This method actually returns an Object[], which is not really proper
   * as it won't throw ArrayStoreExceptions when it should. But the alternative is reflection,
   * which is much slower.
   */
  @Override
  public T[] deserialize(ByteSource source) {
    int len = IntegerSerializer.singleton.deserialize(source);
    @SuppressWarnings("unchecked")
    T[] arr = (T[]) new Object[len];
    for (int i = 0; i < len; ++i)
      arr[i] = elementSerializer.deserialize(source);
    return arr;
  }
}
