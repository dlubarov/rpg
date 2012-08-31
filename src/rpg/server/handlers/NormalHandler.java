package rpg.server.handlers;

import rpg.net.msg.Message;
import rpg.server.active.ActivePlayer;
import rpg.server.active.Session;
import rpg.util.Logger;

public abstract class NormalHandler<T extends Message> extends Handler<T> {
  protected abstract void handle(T msg, ActivePlayer sender);

  @Override public void handle(T msg, Session clientSession) {
    if (clientSession.player == null)
      Logger.warning("Received %s client who has not selected a character: %s.",
          msg, clientSession);
    else
      handle(msg, clientSession.player);
  }
}
