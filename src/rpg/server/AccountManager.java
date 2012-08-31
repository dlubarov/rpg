package rpg.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import rpg.game.CombatClass;

public final class AccountManager {
  private AccountManager() {}

  private static final Map<Integer, Account> accountsByID;
  private static final Map<String, Account> accountsByEmail;

  private static final Map<Integer, PlayerCharacter> charactersByID;
  private static final Map<String, PlayerCharacter> charactersByName;

  private static final Map<Integer, ActivePlayer> playersByID;

  static {
    accountsByID = Collections.synchronizedMap(new HashMap<Integer, Account>());
    accountsByEmail = Collections.synchronizedMap(new HashMap<String, Account>());
    charactersByID = Collections.synchronizedMap(new HashMap<Integer, PlayerCharacter>());
    charactersByName = Collections.synchronizedMap(new HashMap<String, PlayerCharacter>());
    playersByID = Collections.synchronizedMap(new HashMap<Integer, ActivePlayer>());

    // TODO: Remove hardcoded account, useful for testing.
    Account daniel = new Account("d@l.com", "abcdef");
    new PlayerCharacter("Wally", daniel, CombatClass.WARRIOR);
    new PlayerCharacter("Maggie", daniel, CombatClass.MAGE);
  }

  public static Account getAccountByID(int id) {
    return accountsByID.get(id);
  }

  public static Account getAccountByEmail(String email) {
    return accountsByEmail.get(email);
  }

  public static PlayerCharacter getCharacterByID(int id) {
    return charactersByID.get(id);
  }

  public static PlayerCharacter getCharacterByName(String name) {
    return charactersByName.get(name);
  }

  public static ActivePlayer getPlayerByID(int id) {
    return playersByID.get(id);
  }

  public static void register(Account account) {
    accountsByID.put(account.id, account);
    accountsByEmail.put(account.email, account);
  }

  public static void register(PlayerCharacter character) {
    charactersByID.put(character.id, character);
    charactersByName.put(character.name, character);
  }

  public static void login(ActivePlayer player) {
    playersByID.put(player.character.id, player);
  }

  public static void logout(ActivePlayer player) {
    playersByID.remove(player.character.id);
  }
}
