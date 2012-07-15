package rpg.server;

import rpg.math.Vector3;

public class ActiveAccount {
  public final Account account;
  Vector3 destination = null;

  public ActiveAccount(Account account) {
    this.account = account;
  }
}
