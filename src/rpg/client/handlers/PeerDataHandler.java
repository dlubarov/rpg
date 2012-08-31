package rpg.client.handlers;

import rpg.client.mode.GameMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.net.msg.s2c.peer.PeerDataMessage;
import rpg.net.msg.s2c.peer.PeerGoodbye;
import rpg.net.msg.s2c.peer.PeerIntroduction;
import rpg.net.msg.s2c.peer.PeerUpdate;
import rpg.util.Logger;

public class PeerDataHandler extends Handler<PeerDataMessage> {
  public static final PeerDataHandler singleton = new PeerDataHandler();

  private PeerDataHandler() {}

  @Override public void handle(PeerDataMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof GameMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
    }

    GameMode gameMode = (GameMode) currentMode;
    for (PeerIntroduction introduction : msg.introdoctions)
        gameMode.handleIntroduction(introduction);
    for (PeerGoodbye goodbye : msg.goodbyes)
        gameMode.handleGoodbye(goodbye);
    for (PeerUpdate update : msg.updates)
        gameMode.handleUpdate(update);
  }
}
