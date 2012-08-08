package rpg.client.mode;

import org.lwjgl.input.Keyboard;
import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedHSpace;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.PasswordBox;
import rpg.client.gfx.widget.TextBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.core.Info;
import rpg.msg.c2s.LoginMessage;
import rpg.net.ToServerMessageSink;

public class LoginMode extends Mode2D {
  public LoginMode() {
    super(createContent());
  }

  private Widget emailBox() {
    return content.getWidget("email");
  }

  private Widget passwordBox() {
    return content.getWidget("password");
  }

  @Override
  public void onKeyDown(int key) {
    switch (key) {
      case Keyboard.KEY_TAB:
        if (emailBox().isFocused()) {
          passwordBox().makeFocused();
        } else if (passwordBox().isFocused()) {
          emailBox().makeFocused();
        }
        break;
      case Keyboard.KEY_RETURN:
        sendLogin();
        break;
    }
  }

  private void sendLogin() {
    String email = content.getValue("email"), password = content.getValue("password");
    LoginMessage msg = new LoginMessage(email, password, Info.versionParts);
    ToServerMessageSink.singleton.sendWithConfirmation(msg, 3);
  }

  private static Widget createContent() {
    return new VBox(
        new FixedHSpace(160),
        new ConstantLabel("Email"),
        new FixedVSpace(2),
        new TextBox("email"),
        new FixedVSpace(15),
        new ConstantLabel("Password"),
        new FixedVSpace(2),
        new PasswordBox("password"),
        new FixedVSpace(15),
        new SignInButton().padSidesFlexible(),
        new FixedVSpace(60),
        new VBox(
            new ConstantLabel("Not registered yet?", Alignment.CENTER_ALIGNED),
            new FixedVSpace(8),
            new CreateAccountButton().padSidesFlexible())
    ).padFlexible();
  }

  private static class SignInButton extends Button {
    public SignInButton() {
      super("Sign In");
    }

    @Override
    public void onClick() {
      ((LoginMode) ModeManager.getCurrentMode()).sendLogin();
    }
  }

  private static class CreateAccountButton extends Button {
    public CreateAccountButton() {
      super("Create Account");
    }

    @Override
    public void onClick() {
      ModeManager.switchTo(new RegistrationMode());
    }
  }
}
