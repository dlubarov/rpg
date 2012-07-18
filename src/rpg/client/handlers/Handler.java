package rpg.client.handlers;

import rpg.msg.Message;

public abstract class Handler<T extends Message> {
  public abstract void handle(T msg);
}
