package rpg.game.realm;

public class Realm {
  public final int id;
  public final double radius;

  public Realm(double radius) {
    this.id = RealmManager.getNextID();
    this.radius = radius;
  }
}
