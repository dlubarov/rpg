package rpg.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import rpg.util.Logger;
import rpg.net.NetConfig;

public final class Server {
  public static final Server singleton = new Server();

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
    new ServerListener(singleton.socket).start();
  }
}
