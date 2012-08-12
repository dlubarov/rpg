package rpg.client.mode;

import java.awt.Color;
import org.lwjgl.input.Keyboard;
import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedHSpace;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.Label;
import rpg.client.gfx.widget.PasswordBox;
import rpg.client.gfx.widget.TextBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.core.Info;
import rpg.msg.c2s.LoginMessage;
import rpg.msg.s2c.LoginErrorMessage;
import rpg.net.ToServerMessageSink;

public class LoginMode extends Mode2D {
  private final Widget content;
  private String errorMessage = "";
  private final TextBox emailBox, passwordBox;

  public LoginMode() {
    emailBox = new TextBox();
    passwordBox = new PasswordBox();
    content = createContent();
    setContentBounds();
  }

  public void setErrorReason(LoginErrorMessage.Reason reason) {
    errorMessage = reason.message;
  }

  @Override public Widget getContent() {
    return content;
  }

  @Override public void onKeyDown(int key) {
    switch (key) {
      case Keyboard.KEY_TAB:
        if (emailBox.isFocused())
          passwordBox.makeFocused();
        else if (passwordBox.isFocused())
          emailBox.makeFocused();
        break;
      case Keyboard.KEY_RETURN:
        sendLogin();
        break;
    }
  }

  private void sendLogin() {
    String email = emailBox.getContent(), password = passwordBox.getContent();
    // TODO: Do as much client-side verification as possible.
    LoginMessage msg = new LoginMessage(email, password, Info.versionParts);
    ToServerMessageSink.singleton.sendWithConfirmation(msg, 3);

    // TODO: Show a "connecting" message and prevent the user from sending more requests. Also timeout.
  }

  private Widget createContent() {
    return new VBox(
        new FixedHSpace(160),
        new ErrorLabel(),
        new FixedVSpace(60),
        new ConstantLabel("Email"),
        new FixedVSpace(2),
        emailBox,
        new FixedVSpace(15),
        new ConstantLabel("Password"),
        new FixedVSpace(2),
        passwordBox,
        new FixedVSpace(15),
        new SignInButton().padSidesFlexible(),
        new FixedVSpace(60),
        new VBox(
            new ConstantLabel("Not registered yet?", Alignment.CENTER_ALIGNED),
            new FixedVSpace(8),
            new CreateAccountButton().padSidesFlexible())
    ).padFlexible();
  }

  private class ErrorLabel extends Label {
    public ErrorLabel() {
      super(Alignment.CENTER_ALIGNED, Color.RED);
    }

    @Override protected String getContent() {
      return errorMessage;
    }
  }

  private class SignInButton extends Button {
    public SignInButton() {
      super("Sign In");
    }

    @Override public void onClick() {
      sendLogin();
    }
  }

  private static class CreateAccountButton extends Button {
    public CreateAccountButton() {
      super("Create Account");
    }

    @Override public void onClick() {
      ModeManager.switchTo(new RegistrationMode());
    }
  }
}
