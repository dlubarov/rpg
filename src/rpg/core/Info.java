package rpg.core;

import java.util.Arrays;
import java.util.List;

public final class Info {
  private Info() {}

  public static final String name = "Yet Another RPG";

  public static final List<Byte> versionParts = Arrays.asList(new Byte[] {0, 1});

  public static String getVersionString() {
    return versionParts.toString(); // TODO: CollectionUtil.implode
  }
}
