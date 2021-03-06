package rpg.test.serialization;

import rpg.test.Test;
import rpg.util.serialization.LongSerializer;

public class LongSerializerTest extends Test {
  private static final int TEST_SIZE = 30;

  @Override protected void run() {
    for (int i = 0; i < TEST_SIZE; ++i) {
      long expected = rng.nextLong();
      byte[] data = LongSerializer.singleton.serialize(expected);
      long result = LongSerializer.singleton.deserialize(data);
      assertEqual(expected, result);
    }
  }
}
