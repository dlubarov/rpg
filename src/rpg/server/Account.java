package rpg.server;

import rpg.math.Vector3;
import rpg.realm.Realm;
import rpg.realm.RealmManager;

public class Account {
  private static Realm STARTING_REALM = RealmManager.getRealmById(0);
  private static Vector3 STARTING_POS = Vector3.ZERO;

  public final int id;
  public final String username;
  public final String password;

  private Realm realm;
  private Vector3 position, velocity, direction;

  public Account(String username, String password) {
    this.id = AccountManager.getNextId();
    this.username = username;
    this.password = password;
    realm = STARTING_REALM;
    position = STARTING_POS;
    AccountManager.register(this);
  }

  public Realm getRealm() {
    return realm;
  }

  public Vector3 getPos() {
    return position;
  }

  public Vector3 getVel() {
    return velocity;
  }

  public Vector3 getDir() {
    return direction;
  }
}
