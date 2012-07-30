package rpg.client.gfx.widget.winow;

import org.lwjgl.opengl.Display;
import rpg.client.FPSManager;
import rpg.client.mode.ModeManager;
import rpg.core.Info;

public final class RootWindow extends Window {
  public static final RootWindow singleton = new RootWindow();

  private RootWindow() {
    super(new CloseButton());
  }

  @Override
  public String getCaption() {
    return String.format("%s v%s [%d FPS]",
        Info.name, Info.getVersionString(), FPSManager.getFps());
  }

  @Override
  public boolean isFocused() {
    return Display.isActive();
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
    ModeManager.getCurrentMode().render();
    WindowManager.singleton.render();
  }
}
