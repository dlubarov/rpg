package rpg.core;

public final class Levels {
  private static final int
      MAX_LEVEL = 120,
      EXP_1 = 20,
      EXP_100 = 20000;

  private static final double B = Math.log(EXP_100 / (double) EXP_1) / (MAX_LEVEL);
  private static final double A = EXP_1 / (Math.exp(B) - 1);

  private Levels() {}

  public static int experienceToLevel(int experience) {
    // TODO: Binary search.
    int level = 1;
    while (experience > levelToExperience(level))
      ++level;
    return level;
  }

  public static int levelToExperience(int level) {
    return (int) Math.round(A * Math.exp(B * level));
  }
}
