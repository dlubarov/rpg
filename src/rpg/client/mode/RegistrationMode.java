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
import rpg.game.Info;
import rpg.net.ToServerMessageSink;
import rpg.net.msg.c2s.RegistrationMessage;
import rpg.net.msg.s2c.RegistrationErrorMessage;

public class RegistrationMode extends Mode2D {
  private final Widget content;
  private final TextBox emailBox, passwordBox, confirmBox;
  private String message = "";

  public RegistrationMode() {
    emailBox = new TextBox();
    passwordBox = new PasswordBox();
    confirmBox = new PasswordBox();
    content = createContent();
    setContentBounds();
  }

  public void receiveError(RegistrationErrorMessage.Reason reason) {
    message = reason.message;
    content.setFrozen(false);
  }

  @Override public Widget getContent() {
    return content;
  }

  @Override public void onKeyDown(int key) {
    switch (key) {
      case Keyboard.KEY_TAB:
        Widget[] fields = new Widget[] {emailBox, passwordBox, confirmBox};
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
    String email = emailBox.getContent(),
        password = passwordBox.getContent(),
        confirm = confirmBox.getContent();
    // TODO: Do as much client-side verification as possible.
    if (confirm.isEmpty()) {
      receiveError(RegistrationErrorMessage.Reason.CONFIRMATION_MISSING);
      return;
    }
    if (!password.equals(confirm)) {
      receiveError(RegistrationErrorMessage.Reason.BAD_CONFIRMATION);
      return;
    }

    RegistrationMessage msg = new RegistrationMessage(email, password, Info.versionParts);
    ToServerMessageSink.singleton.sendWithConfirmation(msg, 3);

    // TODO: Show a "connecting" message and prevent the user from sending more requests. Also timeout.
    message = "Connecting...";
    content.setFrozen(true);
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
        new ConstantLabel("Confirm"),
        new FixedVSpace(2),
        confirmBox,
        new FixedVSpace(15),
        new RegisterButton().padSidesFlexible(),
        new FixedVSpace(60),
        new VBox(
            new ConstantLabel("Already have an account?", Alignment.CENTER_ALIGNED),
            new FixedVSpace(8),
            new SignInButton().padSidesFlexible()
        )
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

  private class RegisterButton extends Button {
    public RegisterButton() {
      super("Register");
    }

    @Override public void onClick() {
      submit();
    }
  }

  private static class SignInButton extends Button {
    public SignInButton() {
      super("Sign In");
    }

    @Override public void onClick() {
      ModeManager.switchTo(new LoginMode());
    }
  }
}
