package rpg.client.handlers;

import rpg.client.mode.GameMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.net.msg.s2c.PeerIntroductionMessage;
import rpg.util.Logger;

public class PeerIntroductionHandler extends Handler<PeerIntroductionMessage> {
  public static final PeerIntroductionHandler singleton = new PeerIntroductionHandler();

  private PeerIntroductionHandler() {}

  @Override public void handle(PeerIntroductionMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof GameMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
      return;
    }

    GameMode gameMode = (GameMode) currentMode;
    for (PeerIntroductionMessage.Part introduction : msg.parts)
      gameMode.handleIntroduction(introduction);
  }
}
