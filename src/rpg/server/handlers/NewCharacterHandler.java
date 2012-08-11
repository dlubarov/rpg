package rpg.server.handlers;

import java.net.InetAddress;
import rpg.core.CharacterSummary;
import rpg.msg.c2s.NewCharacterMessage;
import rpg.msg.s2c.NewCharacterSuccessMessage;
import rpg.net.ToClientMessageSink;
import rpg.server.Account;
import rpg.server.AccountManager;
import rpg.server.PlayerCharacter;

public class NewCharacterHandler extends Handler<NewCharacterMessage> {
  public static final NewCharacterHandler singleton = new NewCharacterHandler();

  private NewCharacterHandler() {}

  @Override public void handle(NewCharacterMessage msg, InetAddress sender) {
    // FIXME: check the new character name is valid and not taken
    Account account = AccountManager.getAccountByLastAddress(sender);
    PlayerCharacter character = new PlayerCharacter(msg.characterName, account, msg.combatClass);
    CharacterSummary characterSummary = new CharacterSummary(character);
    NewCharacterSuccessMessage response = new NewCharacterSuccessMessage(characterSummary);
    new ToClientMessageSink(sender).sendWithConfirmation(response, 3);
  }
}
