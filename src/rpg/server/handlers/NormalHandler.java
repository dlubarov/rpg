package rpg.server.handlers;

import java.net.InetAddress;
import rpg.msg.Message;
import rpg.server.AccountManager;
import rpg.server.ActivePlayer;
import rpg.util.Logger;

public abstract class NormalHandler<T extends Message> extends Handler<T> {
  protected abstract void handle(T msg, ActivePlayer sender);

  @Override
  public void handle(T msg, InetAddress sender) {
    ActivePlayer player = AccountManager.getPlayerByAddr(sender);
    if (player == null)
      Logger.warning("Received %s from unknown address %s", msg, sender);
    else
      handle(msg, player);
  }
}
