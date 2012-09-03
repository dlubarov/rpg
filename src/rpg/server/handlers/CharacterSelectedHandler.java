package rpg.server.handlers;

import rpg.net.ToClientMessageSink;
import rpg.net.msg.c2s.CharacterSelectedMessage;
import rpg.net.msg.s2c.WelcomeMessage;
import rpg.server.account.AccountManager;
import rpg.server.account.PlayerCharacter;
import rpg.server.active.ActivePlayer;
import rpg.server.active.Session;
import rpg.server.active.UpdatePlayerTask;

public class CharacterSelectedHandler extends Handler<CharacterSelectedMessage> {
  public static final CharacterSelectedHandler singleton = new CharacterSelectedHandler();

  private CharacterSelectedHandler() {}

  @Override public void handle(CharacterSelectedMessage msg, final Session clientSession) {
    PlayerCharacter character = AccountManager.getCharacterByID(msg.id);
    WelcomeMessage response = new WelcomeMessage(msg.id, character.name,
        character.combatClass, character.getRealm(), character.getMotionState());
    clientSession.player = new ActivePlayer(clientSession, character);
    new ToClientMessageSink(clientSession).sendWithConfirmation(
        response, 3,
        new Runnable() {
          @Override public void run() {
            new UpdatePlayerTask(clientSession.player);
          }
        },
        null);
  }
}
