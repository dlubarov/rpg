package rpg.client.mode;

import org.lwjgl.input.Keyboard;
import rpg.client.gfx.Texture;
import rpg.client.gfx.TextureCache;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.winow.ChildWindow;
import rpg.client.gfx.widget.winow.CloseButton;
import rpg.core.Info;
import rpg.msg.Message;
import rpg.msg.c2s.LoginRequestMessage;
import rpg.net.ToServerMessageSink;

public class LoginMode extends Mode {
  private String email = "", password = "";

  @Override
  public void onEnter() {
    Keyboard.enableRepeatEvents(true);
    new ChildWindow("Login", new ConstantLabel("Username"), new CloseButton());
    new ChildWindow("Register", new ConstantLabel("Username"), new CloseButton());
  }

  @Override
  public void onKeyDown(int key) {
    Keyboard.enableRepeatEvents(true);
    System.out.println(Keyboard.areRepeatEventsEnabled());
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
  }
}
