package rpg.server.handlers;

import java.net.InetAddress;
import rpg.msg.c2s.CharacterSelectedMessage;
import rpg.msg.s2c.WelcomeMessage;
import rpg.net.ToClientMessageSink;
import rpg.server.AccountManager;
import rpg.server.PlayerCharacter;

public class CharacterSelectedHandler extends Handler<CharacterSelectedMessage> {
  public static final CharacterSelectedHandler singleton = new CharacterSelectedHandler();

  private CharacterSelectedHandler() {}

  @Override public void handle(CharacterSelectedMessage msg, InetAddress sender) {
    PlayerCharacter character = AccountManager.getCharacterById(msg.id);
    WelcomeMessage response = new WelcomeMessage(msg.id, character.getRealm(),
        character.getPos(), character.getVel(), character.getDir());
    new ToClientMessageSink(sender).sendWithConfirmation(response, 3);
  }
}
