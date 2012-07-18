package rpg.net;

import java.util.Random;

public final class UuidGenerator {
  private UuidGenerator() {}

  private static final Random rng = new Random();

  public static int generate() {
    return rng.nextInt();
  }
}
