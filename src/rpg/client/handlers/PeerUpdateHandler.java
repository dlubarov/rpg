package rpg.client.handlers;

import rpg.client.mode.GameMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.net.msg.s2c.PeerUpdateMessage;
import rpg.util.Logger;

public class PeerUpdateHandler extends Handler<PeerUpdateMessage> {
  public static final PeerUpdateHandler singleton = new PeerUpdateHandler();

  private PeerUpdateHandler() {}

  @Override public void handle(PeerUpdateMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof GameMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
      return;
    }

    GameMode gameMode = (GameMode) currentMode;
    for (PeerUpdateMessage.Part update : msg.parts)
        gameMode.handleUpdate(update);
  }
}
