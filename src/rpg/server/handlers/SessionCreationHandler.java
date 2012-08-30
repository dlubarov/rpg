package rpg.server.handlers;

import rpg.net.msg.c2s.SessionCreationMessage;
import rpg.server.Session;

public class SessionCreationHandler extends Handler<SessionCreationMessage> {
  public static final SessionCreationHandler singleton = new SessionCreationHandler();

  private SessionCreationHandler() {}

  @Override public void handle(SessionCreationMessage msg, Session clientSession) {
    // FIXME send confirmation
    ;
  }
}
