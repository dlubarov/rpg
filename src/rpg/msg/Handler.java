package rpg.msg;

public abstract class Handler<T extends Message> {
  public abstract void handle(T msg);
}
