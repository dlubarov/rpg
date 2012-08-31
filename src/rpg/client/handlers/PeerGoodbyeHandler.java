package rpg.client.handlers;

import rpg.client.mode.GameMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.net.msg.s2c.PeerGoodbyeMessage;
import rpg.net.msg.s2c.PeerIntroductionMessage;
import rpg.util.Logger;

public class PeerGoodbyeHandler extends Handler<PeerGoodbyeMessage> {
  public static final PeerGoodbyeHandler singleton = new PeerGoodbyeHandler();

  private PeerGoodbyeHandler() {}

  @Override public void handle(PeerGoodbyeMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof GameMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
    }

    GameMode gameMode = (GameMode) currentMode;
    for (int id : msg.ids)
        gameMode.handleGoodbye(id);
  }
}
