package rpg.client.mode;

import org.lwjgl.input.Keyboard;
import rpg.client.gfx.Texture;
import rpg.client.gfx.TextureCache;
import rpg.client.gfx.font.FontRendererCache;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glScaled;

public class LoginMode extends Mode {
  @Override
  public void onKeyDown(int key) {
    if (key == Keyboard.KEY_RETURN)
      ; // FIXME send login request
  }

  @Override
  public void render() {
    Texture texApple = TextureCache.singleton.get("elephant");
    /*texApple.bind();
    glBegin(GL_QUADS);
    texApple.bind00();
    glVertex3i(0, 0, 0);
    texApple.bind10();
    glVertex3i(2, 0, 0);
    texApple.bind11();
    glVertex3i(2, 0, 2);
    texApple.bind01();
    glVertex3i(0, 0, 2);
    glEnd();*/

    glDisable(GL_DEPTH_TEST);
    glColor4f(0, 1, 0, 1);
    glScaled(1, -1, 1);
    FontRendererCache.singleton.get("Arial-BOLD-48").draw("Hello Thar!!");
  }
}
