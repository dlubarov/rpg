package rpg.test.serialization;

import rpg.math.Vector3;
import rpg.serialization.Vector3Serializer;
import rpg.test.Test;

public class Vector3SerializerTest extends Test {
  private static final int TEST_SIZE = 10;

  @Override protected void run() {
    for (int i = 0; i < TEST_SIZE; ++i) {
      Vector3 expected = randomVector3();
      byte[] data = Vector3Serializer.singleton.serialize(expected);
      Vector3 result = Vector3Serializer.singleton.deserialize(data);
      assertEqual(expected, result);
    }
  }

  private Vector3 randomVector3() {
    return new Vector3(randomDouble(), randomDouble(), randomDouble());
  }

  private double randomDouble() {
    return (rng.nextDouble() - 0.5) * 100;
  }
}
