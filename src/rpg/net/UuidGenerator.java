package rpg.net;

import java.util.Random;

public final class UuidGenerator {
  private UuidGenerator() {}

  private static final Random rng = new Random();

  public static long generate() {
    long uuid;
    do uuid = rng.nextLong();
    while (uuid == 0);
    return uuid;
  }
}
