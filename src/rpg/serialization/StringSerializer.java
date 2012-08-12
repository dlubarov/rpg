package rpg.serialization;

import java.nio.charset.Charset;

public class StringSerializer extends Serializer<String> {
  public static final StringSerializer singleton = new StringSerializer();

  private static final Charset utf8 = Charset.forName("UTF-8");

  private StringSerializer() {}

  @Override public void serialize(String s, ByteSink sink) {
    byte[] data = s.getBytes(utf8);
    IntegerSerializer.singleton.serialize(data.length, sink);
    sink.giveAll(data);
  }

  @Override public String deserialize(ByteSource source) {
    int len = IntegerSerializer.singleton.deserialize(source);
    byte[] data = source.takeN(len);
    return new String(data, utf8);
  }
}
