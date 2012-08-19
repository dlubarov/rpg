package rpg.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import rpg.game.realm.Realm;
import rpg.game.realm.RealmManager;
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

  public static void sendClient(byte[] data, InetAddress clientAddr) {
    try {
      socket.send(new DatagramPacket(data, data.length, clientAddr, NetConfig.PORT_S2C));
    } catch (IOException e) {
      Logger.error(e, "Failed to send message to client %s.", clientAddr);
    }
  }

  public static void main(String[] args) {
    RealmAdmin.init();
    new ServerListener(socket).start();
    Logger.info("Server started, listening for messages...");
  }
}
