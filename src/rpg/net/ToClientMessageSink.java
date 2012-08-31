package rpg.net;

import java.io.IOException;
import java.net.DatagramPacket;
import rpg.server.active.Session;
import rpg.server.core.Server;
import rpg.util.Logger;

public class ToClientMessageSink extends MessageSink {
  private final Session clientSession;

  public ToClientMessageSink(Session clientSession) {
    this.clientSession = clientSession;
  }

  @Override protected void sendRaw(byte[] data) {
    if (!clientSession.isAlive()) {
      Logger.warning("Attempted to send data to dead session: %s.", clientSession);
      return;
    }

    try {
      Server.socket.send(new DatagramPacket(data, data.length,
          clientSession.address, clientSession.port));
    } catch (IOException e) {
      Logger.error(e, "Failed to send message to client %s.", clientSession);
    }
  }
}
