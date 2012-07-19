package rpg.client.mode;

public abstract class Mode {
  public void onEnter() {}
  public void onExit() {}
  public void logic() {}
  public abstract void render();
}
