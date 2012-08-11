package rpg.server;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AccountManager {
  private AccountManager() {}

  private static final Map<Integer, Account> accountsById;
  private static final Map<String, Account> accountsByEmail;
  private static final Map<InetAddress, Account> accountsByLastAddress;

  private static final Map<Integer, PlayerCharacter> charactersByID;
  private static final Map<String, PlayerCharacter> charactersByName;

  private static final Map<Integer, ActivePlayer> playersById;
  private static final Map<InetAddress, ActivePlayer> playersByAddr;

  static {
    accountsById = Collections.synchronizedMap(new HashMap<Integer, Account>());
    accountsByEmail = Collections.synchronizedMap(new HashMap<String, Account>());
    accountsByLastAddress = Collections.synchronizedMap(new HashMap<InetAddress, Account>());
    charactersByID = Collections.synchronizedMap(new HashMap<Integer, PlayerCharacter>());
    charactersByName = Collections.synchronizedMap(new HashMap<String, PlayerCharacter>());
    playersById = Collections.synchronizedMap(new HashMap<Integer, ActivePlayer>());
    playersByAddr = Collections.synchronizedMap(new HashMap<InetAddress, ActivePlayer>());
  }

  public static Account getAccountById(int id) {
    return accountsById.get(id);
  }

  public static Account getAccountByEmail(String email) {
    return accountsByEmail.get(email);
  }

  public static Account getAccountByLastAddress(InetAddress address) {
    return accountsByLastAddress.get(address);
  }

  public static PlayerCharacter getCharacterById(int id) {
    return charactersByID.get(id);
  }

  public static PlayerCharacter getCharacterByName(String name) {
    return charactersByName.get(name);
  }

  public static ActivePlayer getPlayerById(int id) {
    return playersById.get(id);
  }

  public static ActivePlayer getPlayerByAddr(InetAddress addr) {
    return playersByAddr.get(addr);
  }

  public static void register(Account account) {
    accountsById.put(account.id, account);
    accountsByEmail.put(account.email, account);
  }

  public static void register(PlayerCharacter character) {
    charactersByID.put(character.id, character);
    charactersByName.put(character.name, character);
  }
}
