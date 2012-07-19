package rpg.server;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class AccountManager {
  private AccountManager() {}

  private static final AtomicInteger idCounter = new AtomicInteger();

  private static final Map<Integer, Account> accountsById;
  private static final Map<String, Account> accountsByEmail;

  private static final Map<Integer, ActivePlayer> playersById;
  private static final Map<InetAddress, ActivePlayer> playersByAddr;

  static {
    accountsById = Collections.synchronizedMap(new HashMap<Integer, Account>());
    accountsByEmail = Collections.synchronizedMap(new HashMap<String, Account>());
    playersById = Collections.synchronizedMap(new HashMap<Integer, ActivePlayer>());
    playersByAddr = Collections.synchronizedMap(new HashMap<InetAddress, ActivePlayer>());
  }

  public static int getNextId() {
    return idCounter.getAndIncrement();
  }

  public static Account getAccountById(int id) {
    return accountsById.get(id);
  }

  public static Account getAccountByEmail(String email) {
    return accountsByEmail.get(email);
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
}
