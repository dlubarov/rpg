package rpg.net;

import java.net.InetAddress;

public class ToClientMessageSink extends MessageSink {
  private final InetAddress address;

  public ToClientMessageSink(InetAddress address) {
    this.address = address;
  }

  @Override
  protected void sendRaw(byte[] data) {
    // FIXME
  }
}
