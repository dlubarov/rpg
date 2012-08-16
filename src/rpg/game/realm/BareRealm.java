package rpg.game.realm;

import rpg.util.math.AAB;
import rpg.util.math.Vector3;

public class BareRealm extends Realm {
  private static final double DEFAULT_RAD = 100000;

  public BareRealm(AAB boundingBox) {
    super(boundingBox);
  }

  public BareRealm() {
    this(new AAB(
        new Vector3(-DEFAULT_RAD, -DEFAULT_RAD, -DEFAULT_RAD),
        new Vector3(DEFAULT_RAD, DEFAULT_RAD, DEFAULT_RAD)));
  }
}
