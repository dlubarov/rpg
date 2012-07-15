package rpg.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import rpg.core.Logger;
import rpg.core.NetConfig;

public final class Server {
  private static Server singleton = null;

  public static Server getSingleton() { return singleton; }

  private final DatagramSocket socket;

  private Server() {
    try {
      socket = new DatagramSocket(NetConfig.PORT_C2S);
    } catch (SocketException e) {
      throw new RuntimeException(e);
    }
  }

  public void sendClient(byte[] data, InetAddress clientAddr) {
    try {
      socket.send(new DatagramPacket(data, data.length, clientAddr, NetConfig.PORT_S2C));
    } catch (IOException e) {
      Logger.error(e, "Failed to send message to client.");
    }
  }

  public static void main(String[] args) {
    singleton = new Server();
  }
}
