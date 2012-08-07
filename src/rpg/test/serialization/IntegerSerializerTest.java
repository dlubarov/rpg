package rpg.test.serialization;

import rpg.serialization.IntegerSerializer;
import rpg.test.Test;

public class IntegerSerializerTest extends Test {

  @Override protected void run() {
    for (int i = 0; i < 20; ++i) {
      int randInt = rng.nextInt();
      byte[] byteArray = IntegerSerializer.singleton.serialize(randInt);
      int deserialized = IntegerSerializer.singleton.deserialize(byteArray);
      assertEqual(randInt, deserialized);
    }
  }
}
