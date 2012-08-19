package rpg.game.realm;

public class BareRealm extends Realm {
  private static final double DEFAULT_RAD = 100000;

  public BareRealm(double radius) {
    super(radius);
  }

  public BareRealm() {
    this(DEFAULT_RAD);
  }
}
