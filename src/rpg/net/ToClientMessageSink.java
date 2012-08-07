package rpg.net;

import java.net.InetAddress;
import rpg.server.Server;

public class ToClientMessageSink extends MessageSink {
  private final InetAddress clientAddr;

  public ToClientMessageSink(InetAddress clientAddr) {
    this.clientAddr = clientAddr;
  }

  @Override
  protected void sendRaw(byte[] data) {
    Server.sendClient(data, clientAddr);
  }
}
