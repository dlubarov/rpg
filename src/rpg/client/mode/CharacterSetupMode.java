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
import rpg.msg.c2s.NewCharacterMessage;
import rpg.msg.s2c.NewCharacterErrorMessage;
import rpg.net.ToServerMessageSink;
import rpg.util.Logger;

public class CharacterSetupMode extends Mode2D {
  private final List<CharacterSummary> existingCharacters;
  private final Widget content;
  private TextBox characterName;
  private OptionList<CombatClass> combatClasses;

  public CharacterSetupMode(List<CharacterSummary> existingCharacters) {
    this.existingCharacters = existingCharacters;
    content = createContent();
    setContentBounds();
  }

  public void receivedSuccess(CharacterSummary characterSummary) {
    existingCharacters.add(characterSummary);
    ModeManager.switchTo(new CharacterSelectMode(existingCharacters));
  }

  public void receivedError(NewCharacterErrorMessage.Reason reason) {
    // FIXME: handle
  }

  @Override public Widget getContent() {
    return content;
  }

  private Widget createContent() {
    return new VBox(
        new HBox(
          new VBox(
              new ConstantLabel("Character Name"),
              new FixedVSpace(2),
              characterName = new TextBox(),
              FlexibleSpace.singleton
          ),
          new FixedHSpace(50),
          new VBox(
              new ConstantLabel("Combat Class"),
              new FixedVSpace(2),
              combatClasses = new OptionList<CombatClass>(CombatClass.values()),
              FlexibleSpace.singleton
          )
        ),
        new FixedVSpace(50),
        new CreateCharacterButton().padSidesFlexible(),
        new FixedVSpace(50),
        new BackToCharacterSelectButton().padSidesFlexible()
    ).padFlexible();
  }

  private class CreateCharacterButton extends Button {
    public CreateCharacterButton() {
      super("Create Character");
    }

    @Override protected void onClick() {
      // TODO: show actual errors instead of logging

      if (characterName.getContent().isEmpty()) {
        Logger.info("Name must be some characters!");
        return;
      }
      if (combatClasses.getSelected() == null) {
        Logger.info("Must select a character class!");
        return;
      }

      // make a new character on the server
      NewCharacterMessage msg = new NewCharacterMessage(characterName.getContent(),
          combatClasses.getSelected());
      ToServerMessageSink.singleton.sendWithConfirmation(msg, 3);

      // TODO: Show waiting message and don't let the user send another request
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
