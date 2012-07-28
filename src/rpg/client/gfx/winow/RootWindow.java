package rpg.client.gfx.winow;

import org.lwjgl.opengl.Display;
import rpg.client.mode.ModeManager;
import rpg.core.Info;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public final class RootWindow extends Window {
  public static final RootWindow singleton = new RootWindow();

  private RootWindow() {
    super(Info.name);
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
  protected void renderBackground() {
    glClearColor(1, .7f, .7f, 0);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  @Override
  protected void renderContent() {
    ModeManager.getCurrentMode().render();
  }
}
