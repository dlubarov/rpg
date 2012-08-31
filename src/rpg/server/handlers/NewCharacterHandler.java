package rpg.server.handlers;

import rpg.game.CharacterSummary;
import rpg.net.NetConfig;
import rpg.net.ToClientMessageSink;
import rpg.net.msg.c2s.NewCharacterMessage;
import rpg.net.msg.s2c.NewCharacterErrorMessage;
import rpg.net.msg.s2c.NewCharacterSuccessMessage;
import rpg.server.account.AccountManager;
import rpg.server.account.PlayerCharacter;
import rpg.server.active.Session;

public class NewCharacterHandler extends Handler<NewCharacterMessage> {
  public static final NewCharacterHandler singleton = new NewCharacterHandler();

  private NewCharacterHandler() {}

  @Override public void handle(NewCharacterMessage msg, Session clientSession) {
    NewCharacterErrorMessage.Reason errorReason = getErrorReason(msg.characterName);
    if (errorReason != null) {
      NewCharacterErrorMessage response = new NewCharacterErrorMessage(errorReason);
      new ToClientMessageSink(clientSession).sendWithConfirmation(response, 3);
      return;
    }

    if (clientSession.account == null)
      throw new IllegalStateException("Client not signed in.");

    PlayerCharacter character = new PlayerCharacter(msg.characterName,
        clientSession.account, msg.combatClass);

    CharacterSummary characterSummary = new CharacterSummary(character);
    NewCharacterSuccessMessage response = new NewCharacterSuccessMessage(characterSummary);
    new ToClientMessageSink(clientSession).sendWithConfirmation(response, 3);
  }

  private static NewCharacterErrorMessage.Reason getErrorReason(String name) {
    if (name.isEmpty())
      return NewCharacterErrorMessage.Reason.NAME_MISSING;
    if (name.length() < NetConfig.NAME_MIN_LEN)
      return NewCharacterErrorMessage.Reason.NAME_SHORT;
    if (name.length() > NetConfig.NAME_MAX_LEN)
      return NewCharacterErrorMessage.Reason.NAME_LONG;
    if (!NetConfig.allValidNameCharacters(name))
      return NewCharacterErrorMessage.Reason.NAME_ILLEGAL;
    if (AccountManager.getCharacterByName(name) != null)
      return NewCharacterErrorMessage.Reason.NAME_TAKEN;
    return null;
  }
}
