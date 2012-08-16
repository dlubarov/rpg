package rpg.client.mode;

import java.awt.Color;
import java.util.List;
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
import rpg.game.CharacterSummary;
import rpg.game.Info;
import rpg.net.ToServerMessageSink;
import rpg.net.msg.c2s.LoginMessage;
import rpg.net.msg.s2c.LoginErrorMessage;

public class LoginMode extends Mode2D {
  private final Widget content;
  private final TextBox emailBox, passwordBox;
  private String message = "";

  public LoginMode() {
    emailBox = new TextBox();
    passwordBox = new PasswordBox();
    content = createContent();
    setContentBounds();
  }

  public void receiveError(LoginErrorMessage.Reason reason) {
    message = reason.message;
    content.setFrozen(false);
  }

  @Override public Widget getContent() {
    return content;
  }

  @Override public void onKeyDown(int key) {
    switch (key) {
      case Keyboard.KEY_TAB:
        Widget[] fields = new Widget[] {emailBox, passwordBox};
        for (int i = 0; i < fields.length; ++i)
          if (fields[i].isFocused()) {
            boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
                         || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            int next = (i + (shift ? -1 : 1) + fields.length) % fields.length;
            fields[next].makeFocused();
            return;
          }
        break;
      case Keyboard.KEY_RETURN:
        submit();
        break;
    }
  }

  private void submit() {
    String email = emailBox.getContent(), password = passwordBox.getContent();
    // TODO: Do as much client-side verification as possible.
    LoginMessage msg = new LoginMessage(email, password, Info.versionParts);
    ToServerMessageSink.singleton.sendWithConfirmation(msg, 3);

    // TODO: Timeout.
    message = "Connecting to server...";
    content.setFrozen(true);
  }

  public void receiveSuccess(List<CharacterSummary> summaries) {
    ModeManager.switchTo(new CharacterSelectMode(summaries));
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
      return message;
    }
  }

  private class SignInButton extends Button {
    public SignInButton() {
      super("Sign In");
    }

    @Override public void onClick() {
      submit();
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
