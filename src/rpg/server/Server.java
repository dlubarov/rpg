package rpg.server;

import java.net.DatagramSocket;
import java.net.SocketException;
import rpg.net.NetConfig;
import rpg.util.Logger;

public final class Server {
  public static final DatagramSocket socket;

  static {
    try {
      socket = new DatagramSocket(NetConfig.PORT_C2S);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    RealmAdmin.init();
    new ServerListener(socket).start();
    Logger.info("Server started, listening for messages...");
  }
}
