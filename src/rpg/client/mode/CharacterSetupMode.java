package rpg.client.mode;

import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.HBox;
import rpg.client.gfx.widget.TextBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;

public class CharacterSetupMode extends Mode2D {
  private final Widget content;

  protected CharacterSetupMode() {
    content = createContent();
    setContentBounds();
  }

  @Override
  public Widget getContent() {
    return content;
  }

  private static Widget createContent() {
    return new HBox(
        new VBox(
            new ConstantLabel("Character Name"),
            new FixedVSpace(2),
            new TextBox("characterName")
        ),
        new VBox(
            new ConstantLabel("Combat Class")
        )
    ).padFlexible();
  }
}
