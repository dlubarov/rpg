package rpg.core;

import rpg.util.ArrayUtil;

public final class Info {
  public static final String name = "RPG";

  public static final Byte[] versionParts = {0, 1};

  public String versionString() {
    return ArrayUtil.implode('.', versionParts);
  }
}
