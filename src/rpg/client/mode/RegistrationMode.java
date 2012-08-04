package rpg.client.mode;

import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedHSpace;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.TextBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.core.Info;
import rpg.msg.c2s.RegistrationMessage;
import rpg.net.ToServerMessageSink;

public class RegistrationMode extends Mode2D {
  public RegistrationMode() {
    super(getContent());
  }

  private static Widget getContent() {
    return new VBox(
        new FixedHSpace(160),
        new ConstantLabel("Email"),
        new FixedVSpace(2),
        new TextBox("email"),
        new FixedVSpace(15),
        new ConstantLabel("Password"),
        new FixedVSpace(2),
        new TextBox("password"),
        new FixedVSpace(15),
        new ConstantLabel("Confirm"),
        new FixedVSpace(2),
        new TextBox("confirm"),
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
      Widget form = ((RegistrationMode) ModeManager.getCurrentMode()).content;
      String email = form.getValue("email"),
          password = form.getValue("password"),
          confirm = form.getValue("confirm");
      if (!password.equals(confirm))
        ; // FIXME: display error
      RegistrationMessage msg = new RegistrationMessage(email, password, Info.versionParts);
      ToServerMessageSink.singleton.sendWithConfirmation(msg, 3);
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
