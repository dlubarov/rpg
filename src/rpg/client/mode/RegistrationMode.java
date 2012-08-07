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
import rpg.msg.c2s.RegistrationMessage;
import rpg.net.ToServerMessageSink;

public class RegistrationMode extends Mode2D {
  public RegistrationMode() {
    super(createContent());
  }

  private Widget emailBox() {
    return content.getWidget("email");
  }

  private Widget passwordBox() {
    return content.getWidget("password");
  }

  private Widget confirmBox() {
    return content.getWidget("confirm");
  }

  @Override
  public void onKeyDown(int key) {
    switch (key) {
      case Keyboard.KEY_TAB:
        if (emailBox().isFocused())
          passwordBox().makeFocused();
        else if (passwordBox().isFocused())
          confirmBox().makeFocused();
        else if (confirmBox().isFocused())
          emailBox().makeFocused();
        break;
      case Keyboard.KEY_RETURN:
        sendRegistration();
        break;
    }
  }

  private void sendRegistration() {
    String email = content.getValue("email"),
        password = content.getValue("password"),
        confirm = content.getValue("confirm");
    if (!password.equals(confirm))
      ; // FIXME: display error
    RegistrationMessage msg = new RegistrationMessage(email, password, Info.versionParts);
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
        new ConstantLabel("Confirm"),
        new FixedVSpace(2),
        new PasswordBox("confirm"),
        new FixedVSpace(15),
        new RegisterButton().padSidesFlexible(),
        new FixedVSpace(60),
        new VBox(
            new ConstantLabel("Already have an account?", Alignment.CENTER_ALIGNED),
            new FixedVSpace(8),
            new SignInButton().padSidesFlexible())
    ).padFlexible();
  }

  private static class RegisterButton extends Button {
    public RegisterButton() {
      super("Register");
    }

    @Override
    public void onClick() {
      ((RegistrationMode) ModeManager.getCurrentMode()).sendRegistration();
    }
  }

  private static class SignInButton extends Button {
    public SignInButton() {
      super("Sign In");
    }

    @Override
    public void onClick() {
      ModeManager.switchTo(new LoginMode());
    }
  }
}
