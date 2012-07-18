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
  private static final Map<String, Account> accountsByUsername;

  private static final Map<Integer, ServerPlayer> playersById;
  private static final Map<InetAddress, ServerPlayer> playersByAddr;

  static {
    accountsById = Collections.synchronizedMap(new HashMap<Integer, Account>());
    accountsByUsername = Collections.synchronizedMap(new HashMap<String, Account>());
    playersById = Collections.synchronizedMap(new HashMap<Integer, ServerPlayer>());
    playersByAddr = Collections.synchronizedMap(new HashMap<InetAddress, ServerPlayer>());
  }

  public static int getNextId() {
    return idCounter.getAndIncrement();
  }

  public static Account getAccountById(int id) {
    return accountsById.get(id);
  }

  public static Account getAccountByUsername(String username) {
    return accountsByUsername.get(username);
  }

  public static ServerPlayer getPlayerById(int id) {
    return playersById.get(id);
  }

  public static ServerPlayer getPlayerByAddr(InetAddress addr) {
    return playersByAddr.get(addr);
  }

  public static void register(Account account) {
    accountsById.put(account.id, account);
    accountsByUsername.put(account.username, account);
  }
}
