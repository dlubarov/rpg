package rpg.game.realm;

import java.util.concurrent.atomic.AtomicInteger;

public final class RealmManager {
  private RealmManager() {}

  private static final AtomicInteger idCounter = new AtomicInteger();

  public static final Realm[] allRealms = new Realm[] {
      new BareRealm(),
      new BareRealm(),
      new BareRealm(),
  };

  public static int getNextId() {
    return idCounter.getAndIncrement();
  }

  public static Realm getRealmById(int id) {
    return allRealms[id];
  }
}
