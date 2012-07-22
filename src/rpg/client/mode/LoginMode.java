package rpg.client.mode;

import org.lwjgl.input.Keyboard;
import rpg.client.gfx.Texture;
import rpg.client.gfx.TextureCache;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3i;

public class LoginMode extends Mode {
  @Override
  public void onKeyDown(int key) {
    if (key == Keyboard.KEY_RETURN)
      ; // FIXME send login request
  }

  @Override
  public void render() {
    glColor3f(1, 1, 1);
    Texture texApple = TextureCache.singleton.get("dice");
    texApple.bind();
    glBegin(GL_QUADS);
    texApple.bind00();
    glVertex3i(0, 0, 0);
    texApple.bind10();
    glVertex3i(5, 0, 0);
    texApple.bind11();
    glVertex3i(5, 0, 5);
    texApple.bind01();
    glVertex3i(0, 0, 5);
    glEnd();
  }
}
