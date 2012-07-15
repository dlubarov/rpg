package rpg.realm;

public class Realm {
  public final int id;

  public Realm() {
    this.id = RealmManager.getNextId();
  }
}
