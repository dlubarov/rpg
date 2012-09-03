package rpg.server.active;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import rpg.game.realm.Realm;
import rpg.game.realm.RealmManager;
import rpg.util.ToStringBuilder;
import rpg.util.math.Vector3;
import rpg.util.phys.PointOctree;

public class RealmAdmin {
  private static final Map<Realm, RealmAdmin> admins = new HashMap<Realm, RealmAdmin>();

  public final Realm realm;
  private final PointOctree<ActivePlayer> octree;

  public RealmAdmin(Realm realm) {
    this.realm = realm;
    octree = new PointOctree<ActivePlayer>(Vector3.ZERO, realm.radius);
  }

  public static void init() {
    assert admins.isEmpty();
    for (Realm realm : RealmManager.allRealms)
      admins.put(realm, new RealmAdmin(realm));
  }

  public static synchronized RealmAdmin getAdminFor(Realm realm) {
    return admins.get(realm);
  }

  public void playerEntering(ActivePlayer player) {
    octree.add(player);
  }

  public void playerExiting(ActivePlayer player) {
    octree.remove(player);
  }

  public Collection<ActivePlayer> getNeighbors(ActivePlayer player, double radius) {
    return octree.getNeighbors(player, radius);
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("realm", realm)
        .append("octree", octree)
        .toString();
  }
}
