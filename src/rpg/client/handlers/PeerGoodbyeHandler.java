package rpg.client.handlers;

import rpg.msg.s2c.PeerGoodbyeMessage;

public class PeerGoodbyeHandler extends Handler<PeerGoodbyeMessage> {
  public static final PeerGoodbyeHandler singleton = new PeerGoodbyeHandler();

  private PeerGoodbyeHandler() {}

  @Override public void handle(PeerGoodbyeMessage msg) {
    // FIXME handle
  }
}
