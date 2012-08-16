package rpg.server;

import rpg.game.realm.Realm;
import rpg.util.ToStringBuilder;

public class RealmAdmin {
  public final Realm realm;

  public RealmAdmin(Realm realm) {
    this.realm = realm;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("realm", realm)
        .toString();
  }
}
