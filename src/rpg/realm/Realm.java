package rpg.realm;

import rpg.math.AAB;

public class Realm {
  public final int id;
  public final AAB boundingBox;

  public Realm(AAB boundingBox) {
    this.id = RealmManager.getNextId();
    this.boundingBox = boundingBox;
  }
}
