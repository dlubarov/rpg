package rpg.client.gfx.widget.winow;

import org.lwjgl.opengl.Display;
import rpg.client.gfx.GraphicsMode;
import rpg.client.mode.ModeManager;
import rpg.core.Info;

public final class RootWindow extends Window {
  public static final RootWindow singleton = new RootWindow();

  private RootWindow() {
    super(Info.name, new CloseButton());
  }

  @Override
  protected int x1() {
    return 0;
  }

  @Override
  protected int y1() {
    return 0;
  }

  @Override
  protected int contentW() {
    return Display.getWidth();
  }

  @Override
  protected int contentH() {
    return Display.getHeight() - BAR_HEIGHT;
  }

  @Override
  protected void renderContent() {
    GraphicsMode.end2D();
    ModeManager.getCurrentMode().render();
    GraphicsMode.start2D();
    ChildManager.singleton.render();
  }
}
