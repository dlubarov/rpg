package rpg.client.mode.menu;

import org.lwjgl.util.glu.GLU;
import rpg.client.gfx.GraphicsMode;
import rpg.client.gfx.Texture;
import rpg.client.gfx.TextureCache;
import rpg.client.gfx.widget.winow.ChildWindow;
import rpg.client.mode.Mode;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3i;

public class MenuMode extends Mode {
  private ChildWindow loginWindow = null, registrationWindow = null;

  @Override
  public void onEnter() {
    loginWindow = Login.createWindow();
    registrationWindow = Registration.createWindow();
  }

  @Override
  public void onExit() {
    loginWindow.hide();
    registrationWindow.hide();
  }

  @Override
  public void render() {
    GraphicsMode.end2D();
    GLU.gluLookAt(1, 50, 0,
        0, 0, 0,
        0, 1, 0);
    glEnable(GL_TEXTURE_2D);
    Texture texApple = TextureCache.singleton.get("elephant");
    texApple.bind();
    glBegin(GL_QUADS);
    glColor3f(1, 0, 0);
    texApple.bind00();
    glVertex3i(-14, 0, -14);
    texApple.bind10();
    glVertex3i(14, 0, -14);
    texApple.bind11();
    glVertex3i(14, 0, 14);
    texApple.bind01();
    glVertex3i(-14, 0, 14);
    glEnd();
    GraphicsMode.start2D();
  }
}
