package rpg.client.gfx;

public abstract class Renderable2D {
  protected abstract void render2D();

  public void render() {
    GraphicsMode.start2D();
    render2D();
    GraphicsMode.end2D();
  }
}
