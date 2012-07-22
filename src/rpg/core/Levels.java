package rpg.core;

public final class Levels {
  private Levels() {}

  public static int getLevel(long experience) {
    // TODO: Precompute, memoize or at least binary search.
    int level = 1;
    while (experience > getExperienceFor(level))
      ++level;
    return level;
  }

  public static long getExperienceFor(int level) {
    // FIXME: Come up with a proper leveling system.
    return level * 10;
  }
}
