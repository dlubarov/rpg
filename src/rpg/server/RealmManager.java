package rpg.server;

import rpg.realm.Realm;
import rpg.util.ToStringBuilder;

public class RealmManager {
  public final Realm realm;

  public RealmManager(Realm realm) {
    this.realm = realm;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("realm", realm)
        .toString();
  }
}
