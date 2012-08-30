package rpg.server.handlers;

import rpg.net.msg.Message;
import rpg.server.Session;

public abstract class Handler<T extends Message> {
  public abstract void handle(T msg, Session clientSession);
}
