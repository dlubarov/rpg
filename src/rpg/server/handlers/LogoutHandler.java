package rpg.server.handlers;

import rpg.net.msg.c2s.LogoutMessage;
import rpg.server.active.Session;

public class LogoutHandler extends Handler<LogoutMessage> {
  public static final LogoutHandler singleton = new LogoutHandler();

  private LogoutHandler() {}

  @Override public void handle(LogoutMessage msg, Session clientSession) {
    // TODO: Review this...
    clientSession.account = null;
    clientSession.player = null;
  }
}
