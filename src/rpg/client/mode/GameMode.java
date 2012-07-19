package rpg.client.mode;

import rpg.math.AAB;
import rpg.math.Vector3;
import rpg.phys.BodyOctree;
import rpg.realm.Realm;

public class GameMode extends Mode {
  private Realm realm;
  private BodyOctree octree;

  public Realm getRealm() {
    return realm;
  }

  public void setRealm(Realm realm) {
    this.realm = realm;
    AAB bb = realm.boundingBox;
    Vector3 center = bb.min.averagedWith(bb.max);
    Vector3 size = bb.max.minus(bb.min);
    octree = new BodyOctree(center, size.max() / 2);
  }

  @Override
  public void render() {
    // FIXME render
  }
}
