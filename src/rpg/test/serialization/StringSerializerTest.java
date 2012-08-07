package rpg.test.serialization;

import rpg.serialization.StringSerializer;
import rpg.test.Test;

public class StringSerializerTest extends Test {
  @Override protected void run() {
    String[] stringList = {"Hello, world!", "abc123"};
    for (String s : stringList) {
      byte[] byteArray = StringSerializer.singleton.serialize(s);
      String deserialized = StringSerializer.singleton.deserialize(byteArray);
      assertEqual(s, deserialized);
    }
  }
}
