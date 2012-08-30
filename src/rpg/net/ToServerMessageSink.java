package rpg.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Random;
import rpg.client.Client;
import rpg.util.Logger;

public class ToServerMessageSink extends MessageSink {
  public static final ToServerMessageSink singleton = new ToServerMessageSink();

  private long sessionID;

  private ToServerMessageSink() {
    sessionID = new Random().nextLong();
  }

  @Override protected void sendRaw(byte[] data) {
    try {
      Client.socket.send(new DatagramPacket(data, data.length,
          NetConfig.serverAddr, NetConfig.PORT_C2S));
    } catch (IOException e) {
      Logger.error(e, "Failed to send packet to server.");
    }
  }

  @Override protected Long getSessionID() {
    return sessionID;
  }
}
