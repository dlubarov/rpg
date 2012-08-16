package rpg.client.mode;

import java.util.List;
import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.HBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.game.CharacterSummary;
import rpg.net.ToServerMessageSink;
import rpg.net.msg.c2s.CharacterSelectedMessage;

public class CharacterSelectMode extends Mode2D {
  private final List<CharacterSummary> options;
  private final Widget content;

  public CharacterSelectMode(List<CharacterSummary> options) {
    this.options = options;
    content = createContent();
    setContentBounds();
  }

  @Override public Widget getContent() {
    return content;
  }

  private Widget createContent() {
    Widget[] parts = new Widget[options.size()];
    for (int i = 0; i < parts.length; ++i)
      parts[i] = createBoxFor(options.get(i));
    HBox characters = new HBox(parts);
    return new VBox(
        characters.padSidesFlexible(),
        new FixedVSpace(50),
        new NewCharacterButton().padSidesFlexible()
    ).padFlexible();
  }

  private Widget createBoxFor(CharacterSummary character) {
    return new VBox(
        new ConstantLabel(character.name).padSidesFlexible(),
        new FixedVSpace(10),
        new ConstantLabel(character.combatClass.toString()).padSidesFlexible(),
        new FixedVSpace(10),
        new CharacterSelectionButton(character).padSidesFlexible()
    ).pad(15, 15, 0, 0);
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

    @Override protected void onClick() {
      CharacterSelectedMessage msg = new CharacterSelectedMessage(character.id);
      ToServerMessageSink.singleton.sendWithConfirmation(msg, 3);
    }
  }
}
