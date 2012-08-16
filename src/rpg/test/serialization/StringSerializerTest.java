package rpg.test.serialization;

import rpg.test.Test;
import rpg.util.serialization.StringSerializer;

public class StringSerializerTest extends Test {
  private static final int TEST_SIZE = 10;

  @Override protected void run() {
    for (int i = 0; i < TEST_SIZE; ++i) {
      String expected = randomString(15);
      byte[] data = StringSerializer.singleton.serialize(expected);
      String result = StringSerializer.singleton.deserialize(data);
      assertEqual(expected, result);
    }
  }

  private String randomString(int expectedLength) {
    StringBuilder sb = new StringBuilder();
    double pContinue = expectedLength / (1 + expectedLength);
    while (rng.nextDouble() < pContinue)
      sb.append(randomChar());
    return sb.toString();
  }

  private char randomChar() {
    char c;
    do c = (char) rng.nextInt(Character.MAX_VALUE);
    while (!Character.isDefined(c) || Character.isLowSurrogate(c) || Character.isHighSurrogate(c));
    return c;
  }
}
