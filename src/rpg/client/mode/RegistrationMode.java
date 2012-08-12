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
import rpg.msg.c2s.RegistrationMessage;
import rpg.msg.s2c.RegistrationErrorMessage;
import rpg.net.ToServerMessageSink;

public class RegistrationMode extends Mode2D {
  private final Widget content;
  private String errorMessage = "";
  private final TextBox emailBox, passwordBox, confirmBox;

  public RegistrationMode() {
    emailBox = new TextBox();
    passwordBox = new PasswordBox();
    confirmBox = new PasswordBox();
    content = createContent();
    setContentBounds();
  }

  public void setErrorReason(RegistrationErrorMessage.Reason reason) {
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
          confirmBox.makeFocused();
        else if (confirmBox.isFocused())
          emailBox.makeFocused();
        break;
      case Keyboard.KEY_RETURN:
        sendRegistration();
        break;
    }
  }

  private void sendRegistration() {
    String email = emailBox.getContent(),
        password = passwordBox.getContent(),
        confirm = confirmBox.getContent();
    // TODO: Do as much client-side verification as possible.
    if (confirm.isEmpty()) {
      setErrorReason(RegistrationErrorMessage.Reason.CONFIRMATION_MISSING);
      return;
    }
    if (!password.equals(confirm)) {
      setErrorReason(RegistrationErrorMessage.Reason.BAD_CONFIRMATION);
      return;
    }

    RegistrationMessage msg = new RegistrationMessage(email, password, Info.versionParts);
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
        new ConstantLabel("Confirm"),
        new FixedVSpace(2),
        confirmBox,
        new FixedVSpace(15),
        new RegisterButton().padSidesFlexible(),
        new FixedVSpace(60),
        new VBox(
            new ConstantLabel("Already have an account?", Alignment.CENTER_ALIGNED),
            new FixedVSpace(8),
            new SignInButton().padSidesFlexible())
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

  private class RegisterButton extends Button {
    public RegisterButton() {
      super("Register");
    }

    @Override public void onClick() {
      sendRegistration();
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
