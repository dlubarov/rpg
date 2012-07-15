package rpg.core;

public final class Info {
  public static String name = "RPG";

  public static final byte[] versionParts = {0, 1};

  public String versionString() {
    StringBuilder sb = new StringBuilder().append(versionParts[0]);
    for (int i = 1; i < versionParts.length; ++i)
      sb.append('.').append(versionParts[1]);
    return sb.toString();
  }
}
