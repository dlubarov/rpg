package rpg.core;

import rpg.util.ArrayUtil;

public final class Info {
  private Info() {}

  public static final String name = "Yet Another RPG";

  public static final Byte[] versionParts = {0, 1};

  public static String getVersionString() {
    return ArrayUtil.implode('.', versionParts);
  }
}
