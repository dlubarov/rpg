package rpg.test.serialization;

import rpg.test.Test;
import rpg.util.serialization.IntegerSerializer;

public class IntegerSerializerTest extends Test {
  private static final int TEST_SIZE = 50;

  @Override protected void run() {
    for (int i = 0; i < TEST_SIZE; ++i) {
      int expected = rng.nextInt();
      byte[] data = IntegerSerializer.singleton.serialize(expected);
      int result = IntegerSerializer.singleton.deserialize(data);
      assertEqual(expected, result);
    }
  }
}
