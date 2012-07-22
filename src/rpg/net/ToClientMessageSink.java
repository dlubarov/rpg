package rpg.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import rpg.server.Server;
import rpg.util.Logger;

public class ToClientMessageSink extends MessageSink {
  private final InetAddress clientAddr;

  public ToClientMessageSink(InetAddress clientAddr) {
    this.clientAddr = clientAddr;
  }

  @Override
  protected void sendRaw(byte[] data) {
    try {
      Server.socket.send(new DatagramPacket(data, data.length, clientAddr, NetConfig.PORT_S2C));
    } catch (IOException e) {
      Logger.error(e, "Failed to send message to client %s", clientAddr);
    }
  }
}
