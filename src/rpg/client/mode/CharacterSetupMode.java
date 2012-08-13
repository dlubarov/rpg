package rpg.client.mode;

import java.awt.Color;
import java.util.List;
import org.lwjgl.input.Keyboard;
import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.widget.Button;
import rpg.client.gfx.widget.ConstantLabel;
import rpg.client.gfx.widget.FixedHSpace;
import rpg.client.gfx.widget.FixedVSpace;
import rpg.client.gfx.widget.FlexibleSpace;
import rpg.client.gfx.widget.HBox;
import rpg.client.gfx.widget.Label;
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
  private String message = "";

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
    message = reason.message;
    content.setFrozen(false);
  }

  @Override public Widget getContent() {
    return content;
  }

  @Override public void onKeyDown(int key) {
    switch (key) {
      case Keyboard.KEY_RETURN:
        submit();
        break;
    }
  }

  private void submit() {
    if (characterName.getContent().isEmpty()) {
      message = "Please enter a character name.";
      return;
    }
    if (combatClasses.getSelected() == null) {
      message = "Please select a combat class.";
      return;
    }

    NewCharacterMessage msg = new NewCharacterMessage(characterName.getContent(),
        combatClasses.getSelected());
    ToServerMessageSink.singleton.sendWithConfirmation(msg, 3);

    // TODO: Timeout.
    message = "Communicating with server...";
    content.setFrozen(true);
  }

  private Widget createContent() {
    return new VBox(
        new ErrorLabel(),
        new FixedVSpace(60),
        new HBox(
          new VBox(
              new FixedHSpace(160),
              new ConstantLabel("Character Name"),
              new FixedVSpace(2),
              characterName = new TextBox(),
              FlexibleSpace.singleton
          ),
          new FixedHSpace(50),
          new VBox(
              new FixedHSpace(160),
              new ConstantLabel("Combat Class"),
              new FixedVSpace(2),
              combatClasses = new OptionList<CombatClass>(CombatClass.values()),
              FlexibleSpace.singleton
          )
        ),
        new FixedVSpace(50),
        new CreateCharacterButton().padSidesFlexible(),
        new FixedVSpace(30),
        new BackToCharacterSelectButton().padSidesFlexible()
    ).padFlexible();
  }

  private class ErrorLabel extends Label {
    public ErrorLabel() {
      super(Alignment.CENTER_ALIGNED, Color.RED);
    }

    @Override protected String getContent() {
      return message;
    }
  }

  private class CreateCharacterButton extends Button {
    public CreateCharacterButton() {
      super("Create Character");
    }

    @Override protected void onClick() {
      submit();
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
