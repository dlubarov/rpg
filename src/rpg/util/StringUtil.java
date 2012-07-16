package rpg.util;

public final class StringUtil {
  private StringUtil() {}

  public static String repeat(String s, int times) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < times; ++i)
      sb.append(s);
    return sb.toString();
  }

  public static String repeat(char c, int times) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < times; ++i)
      sb.append(c);
    return sb.toString();
  }

  public static String mask(String s) {
    return repeat('*', s.length());
  }
}
