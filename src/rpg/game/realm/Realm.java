package rpg.game.realm;

import rpg.util.math.AAB;

public class Realm {
  public final int id;
  public final AAB boundingBox;

  public Realm(AAB boundingBox) {
    this.id = RealmManager.getNextId();
    this.boundingBox = boundingBox;
  }
}
