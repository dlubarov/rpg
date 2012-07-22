package rpg.client.mode;

public abstract class Mode {
  public void onEnter() {}
  public void onExit() {}
  public void logic() {}
  public void onKeyDown(int key) {}
  public void onKeyUp(int key) {}
  public abstract void render();
}
