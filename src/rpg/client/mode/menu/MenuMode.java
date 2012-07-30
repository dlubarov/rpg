package rpg.client.mode.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.glu.GLU;
import rpg.client.gfx.GraphicsMode;
import rpg.client.gfx.Texture;
import rpg.client.gfx.TextureCache;
import rpg.client.mode.Mode;
import rpg.core.Info;
import rpg.msg.Message;
import rpg.msg.c2s.LoginRequestMessage;
import rpg.net.ToServerMessageSink;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3i;

public class MenuMode extends Mode {
  private String email = "", password = "";

  @Override
  public void onEnter() {
    Registration.createWindow();
    Login.createWindow();
  }

  @Override
  public void onKeyDown(int key) {
    Keyboard.enableRepeatEvents(true);
    switch (key) {
      case Keyboard.KEY_BACK:
        if (email.length() > 0)
          email = email.substring(0, email.length() - 1);
        break;
      case Keyboard.KEY_RETURN:
        Message loginMsg = new LoginRequestMessage(email, password, Info.versionParts);
        ToServerMessageSink.singleton.sendWithConfirmation(loginMsg, 2);
        break;
      default:
        char c = Keyboard.getEventCharacter();
        System.out.printf("%d %d\n", (int) c, Keyboard.CHAR_NONE);
        if (c != Keyboard.CHAR_NONE) {
          email += c;
        }
    }
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
