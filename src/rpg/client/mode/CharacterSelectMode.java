package rpg.client.mode;

import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.HBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.core.CharacterSummary;

public class CharacterSelectMode extends Mode2D {
  public CharacterSelectMode(CharacterSummary[] options) {
    super(createContent(options));
  }

  private static Widget createContent(CharacterSummary[] options) {
    VBox[] parts = new VBox[options.length];
    for (int i = 0; i < parts.length; ++i)
      parts[i] = new VBox(
          new ConstantLabel(options[i].name),
          new ConstantLabel(options[i].combatClass.name()),
          new CharacterSelectionButton(options[i]));
    return new HBox(parts).padFlexible();
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
