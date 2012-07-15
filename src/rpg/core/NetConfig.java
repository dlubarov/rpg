package rpg.core;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class NetConfig {
  private NetConfig() {}

  public static InetAddress serverAddr;

  static {
    try {
      serverAddr = Inet4Address.getLocalHost();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }

  public static final int
      PORT_C2S = 1337, PORT_S2C = 1338,
      USERNAME_MIN_LEN = 3, USERNAME_MAX_LEN = 32,
      PASSWORD_MIN_LEN = 6, PASSWORD_MAX_LEN = 256;
}
