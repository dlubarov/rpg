package rpg.server.handlers;

import rpg.net.msg.c2s.HereIAmMessage;
import rpg.server.ActivePlayer;
import rpg.util.Logger;

public class HereIAmHandler extends NormalHandler<HereIAmMessage> {
  public static final HereIAmHandler singleton = new HereIAmHandler();

  private HereIAmHandler() {}

  @Override public void handle(HereIAmMessage msg, ActivePlayer sender) {
    sender.character.setMotionState(msg.motionState);
  }
}
