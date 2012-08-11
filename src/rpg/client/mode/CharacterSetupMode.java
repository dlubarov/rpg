package rpg.client.mode;

import java.util.List;
import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedHSpace;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.FlexibleSpace;
import rpg.client.gfx.widget.HBox;
import rpg.client.gfx.widget.OptionList;
import rpg.client.gfx.widget.TextBox;
import rpg.client.gfx.widget.VBox;
import rpg.client.gfx.widget.Widget;
import rpg.core.CharacterSummary;
import rpg.core.CombatClass;

public class CharacterSetupMode extends Mode2D {
  private final List<CharacterSummary> existingCharacters;
  private final Widget content;

  public CharacterSetupMode(List<CharacterSummary> existingCharacters) {
    this.existingCharacters = existingCharacters;
    content = createContent();
    setContentBounds();
  }

  @Override
  public Widget getContent() {
    return content;
  }

  private Widget createContent() {
    return new VBox(
        new HBox(
          new VBox(
              new ConstantLabel("Character Name"),
              new FixedVSpace(2),
              new TextBox(),
              FlexibleSpace.singleton
          ),
          new FixedHSpace(50),
          new VBox(
              new ConstantLabel("Combat Class"),
              new FixedVSpace(2),
              new OptionList<CombatClass>(CombatClass.values()),
              FlexibleSpace.singleton
          )
        ),
        new FixedVSpace(50),
        new CreateCharacterButton().padSidesFlexible(),
        new FixedVSpace(50),
        new BackToCharacterSelectButton().padSidesFlexible()
    ).padFlexible();
  }

  private static class CreateCharacterButton extends Button {
    public CreateCharacterButton() {
      super("Create Character");
    }

    @Override protected void onClick() {
      //ModeManager.switchTo();
    }
  }

  private class BackToCharacterSelectButton extends Button {
    public BackToCharacterSelectButton() {
      super("Back to Character Select");
    }

    @Override protected void onClick() {
      ModeManager.switchTo(new CharacterSelectMode(existingCharacters));
    }
  }
}
