package rpg.client.mode.menu;

import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedHSpace;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.TextBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.client.gfx.widget.winow.ChildWindow;
import rpg.client.gfx.widget.winow.CloseButton;

public final class Registration {
  private Registration() {}

  private static Widget getContent() {
    return new VBox(
        new FixedHSpace(120),
        new ConstantLabel("Username"),
        new FixedVSpace(2),
        new TextBox(),
        new FixedVSpace(15),
        new ConstantLabel("Password"),
        new FixedVSpace(2),
        new TextBox(),
        new FixedVSpace(15),
        new ConstantLabel("Confirm"),
        new FixedVSpace(2),
        new TextBox(),
        new FixedVSpace(15),
        new Button("Register")
    ).pad(10);
  }

  public static ChildWindow createWindow() {
    return new ChildWindow("Register", getContent(), new CloseButton());
  }
}
