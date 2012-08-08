package rpg.client.mode;

import java.util.List;
import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.HBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.core.CharacterSummary;

public class CharacterSelectMode extends Mode2D {
  private final Widget content;

  public CharacterSelectMode(List<CharacterSummary> options) {
    content = createContent(options);
    setContentBounds();
  }

  @Override
  public Widget getContent() {
    return content;
  }

  private static Widget createContent(List<CharacterSummary> options) {
    VBox[] parts = new VBox[options.size()];
    for (int i = 0; i < parts.length; ++i)
      parts[i] = new VBox(
          new ConstantLabel(options.get(i).name),
          new ConstantLabel(options.get(i).combatClass.name()),
          new CharacterSelectionButton(options.get(i)));
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
