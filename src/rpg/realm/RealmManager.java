package rpg.realm;

import java.util.concurrent.atomic.AtomicInteger;

public final class RealmManager {
  private RealmManager() {}

  private static Realm[] realms = new Realm[] {
      new BareRealm(),
      new BareRealm(),
      new BareRealm(),
  };

  private static AtomicInteger idCounter = new AtomicInteger();

  public static int getNextId() {
    return idCounter.getAndIncrement();
  }

  public static Realm getRealmById(int id) {
    return realms[id];
  }
}
