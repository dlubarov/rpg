package rpg.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public final class NetConfig {
  private NetConfig() {}

  public static final int
      PORT_C2S = 1337, PORT_S2C = 1338,
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

  public static final Set<Character> validUsernameChars = new HashSet<Character>();

  static {
    for (char c = 'a'; c <= 'z'; ++c)
      validUsernameChars.add(c);
    for (char c = 'A'; c <= 'Z'; ++c)
      validUsernameChars.add(c);
    for (char c = '0'; c <= '9'; ++c)
      validUsernameChars.add(c);
    validUsernameChars.add('_');
  }

  public static boolean validUsernameCharacter(char c) {
    return validUsernameChars.contains(c);
  }
}
