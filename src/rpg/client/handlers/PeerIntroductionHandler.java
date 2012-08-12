package rpg.client.handlers;

import rpg.msg.s2c.PeerIntroductionMessage;

public class PeerIntroductionHandler extends Handler<PeerIntroductionMessage> {
  public static final PeerIntroductionHandler singleton = new PeerIntroductionHandler();

  private PeerIntroductionHandler() {}

  @Override public void handle(PeerIntroductionMessage msg) {
    // FIXME handle
  }
}
