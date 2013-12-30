package rpg.game;

import java.util.Arrays;
import java.util.List;

public final class Info {
  private Info() {}

  public static final String name = "Yet Another RPG";

  public static final List<Byte> versionParts = Arrays.asList(new Byte[] {0, 1});

  public static String getVersionString() {
    StringBuilder sb = new StringBuilder();
    for (byte b : versionParts) {
      if (sb.length() > 0)
        sb.append('.');
      sb.append(b);
    }
    return sb.toString();
  }
}
