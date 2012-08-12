package rpg.client.handlers;

import rpg.msg.s2c.PeerUpdateMessage;

public class PeerUpdateHandler extends Handler<PeerUpdateMessage> {
  public static final PeerUpdateHandler singleton = new PeerUpdateHandler();

  private PeerUpdateHandler() {}

  @Override public void handle(PeerUpdateMessage msg) {
    // FIXME handle
  }
}
