package rpg.client.mode;

import java.util.List;
import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.HBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.core.CharacterSummary;

public class CharacterSelectMode extends Mode2D {
  List<CharacterSummary> options;
  private final Widget content;

  public CharacterSelectMode(List<CharacterSummary> options) {
    this.options = options;
    content = createContent();
    setContentBounds();
  }

  @Override
  public Widget getContent() {
    return content;
  }

  private Widget createContent() {
    VBox[] parts = new VBox[options.size()];
    for (int i = 0; i < parts.length; ++i)
      parts[i] = new VBox(
          new ConstantLabel(options.get(i).name),
          new ConstantLabel(options.get(i).combatClass.name()),
          new CharacterSelectionButton(options.get(i)));
    HBox characters = new HBox(parts);
    return new VBox(
        characters.padSidesFlexible(),
        new FixedVSpace(50),
        new NewCharacterButton().padSidesFlexible()
    ).padFlexible();
  }

  private class NewCharacterButton extends Button {
    public NewCharacterButton() {
      super("Create New Character");
    }

    @Override protected void onClick() {
      ModeManager.switchTo(new CharacterSetupMode(options));
    }
  }

  private static class CharacterSelectionButton extends Button {
    private final CharacterSummary character;

    public CharacterSelectionButton(CharacterSummary character) {
      super("Select");
      this.character = character;
    }

    @Override
    protected void onClick() {
      // FIXME: select character
    }
  }
}
