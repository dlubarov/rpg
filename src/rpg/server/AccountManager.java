package rpg.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class AccountManager {
  private AccountManager() {}

  private static AtomicInteger idCounter = new AtomicInteger();

  private static final Map<Integer, Account> accountsById;
  private static final Map<String, Account> accountsByUsername;

  static {
    accountsById = Collections.synchronizedMap(new HashMap<Integer, Account>());
    accountsByUsername = Collections.synchronizedMap(new HashMap<String, Account>());
  }

  public static int getNextId() {
    return idCounter.getAndIncrement();
  }

  public static Account getById(int id) {
    return accountsById.get(id);
  }

  public static Account getByUsername(String username) {
    return accountsByUsername.get(username);
  }

  public static void register(Account account) {
    accountsById.put(account.id, account);
    accountsByUsername.put(account.username, account);
  }
}
