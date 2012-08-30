package rpg.client.handlers;

import rpg.net.msg.s2c.peer.PeerDataMessage;

public class PeerDataHandler extends Handler<PeerDataMessage> {
  public static final PeerDataHandler singleton = new PeerDataHandler();

  private PeerDataHandler() {}

  @Override public void handle(PeerDataMessage msg) {
    // FIXME handle
  }
}
