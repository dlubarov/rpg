package rpg.serialization;

import java.util.ArrayList;
import java.util.List;

public class ListSerializer<T> extends Serializer<List<T>> {
  public static final ListSerializer<Byte> byteListSerializer =
      new ListSerializer<Byte>(ByteSerializer.singleton);
  public static final ListSerializer<Integer> integerListSerializer =
      new ListSerializer<Integer>(IntegerSerializer.singleton);

  private final Serializer<T> elementSerializer;

  public ListSerializer(Serializer<T> elementSerializer) {
    this.elementSerializer = elementSerializer;
  }

  @Override
  public void serialize(List<T> list, ByteSink sink) {
    IntegerSerializer.singleton.serialize(list.size(), sink);
    for (T elem : list)
      elementSerializer.serialize(elem, sink);
  }

  @Override
  public List<T> deserialize(ByteSource source) {
    int size = IntegerSerializer.singleton.deserialize(source);
    ArrayList<T> result = new ArrayList<T>(size);
    for (int i = 0; i < size; ++i)
      result.add(elementSerializer.deserialize(source));
    return result;
  }
}
