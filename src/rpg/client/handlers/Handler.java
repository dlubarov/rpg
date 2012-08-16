package rpg.client.handlers;

import rpg.net.msg.Message;

public abstract class Handler<T extends Message> {
  public abstract void handle(T msg);
}
