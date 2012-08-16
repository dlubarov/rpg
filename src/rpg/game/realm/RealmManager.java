package rpg.game.realm;

import java.util.concurrent.atomic.AtomicInteger;

public final class RealmManager {
  private RealmManager() {}

  private static final AtomicInteger idCounter = new AtomicInteger();

  private static final Realm[] realms = new Realm[] {
      new BareRealm(),
      new BareRealm(),
      new BareRealm(),
  };

  public static int getNextId() {
    return idCounter.getAndIncrement();
  }

  public static Realm getRealmById(int id) {
    return realms[id];
  }
}
