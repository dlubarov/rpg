package rpg.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public final class NetConfig {
  private NetConfig() {}

  public static final int
      PORT_C2S = 1337, PORT_S2C_MIN = 1338, PORT_S2C_MAX = 1350,
      EMAIL_MAX_LEN = 256,
      PASSWORD_MIN_LEN = 6, PASSWORD_MAX_LEN = 256,
      NAME_MIN_LEN = 3, NAME_MAX_LEN = 32;

  public static final InetAddress serverAddr;

  static {
    try {
      serverAddr = Inet4Address.getLocalHost();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }

  public static final Set<Character> validNameChars = new HashSet<Character>();

  static {
    // TODO: Allow spaces, but not at start/end and not more than 1 consecutive.
    for (char c = 'a'; c <= 'z'; ++c)
      validNameChars.add(c);
    for (char c = 'A'; c <= 'Z'; ++c)
      validNameChars.add(c);
    for (char c = '0'; c <= '9'; ++c)
      validNameChars.add(c);
    validNameChars.add('_');
  }

  private static boolean validNameCharacter(char c) {
    return validNameChars.contains(c);
  }

  public static boolean allValidNameCharacters(String s) {
    for (char c : s.toCharArray())
      if (!validNameCharacter(c))
        return false;
    return true;
  }
}
