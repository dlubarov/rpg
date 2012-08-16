package rpg.server.handlers;

import java.net.InetAddress;
import rpg.net.msg.Message;

public abstract class Handler<T extends Message> {
  public abstract void handle(T msg, InetAddress sender);
}
