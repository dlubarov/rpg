package rpg.server;

import rpg.math.Vector3;
import rpg.motion.Motion;
import rpg.motion.StillMotion;
import rpg.realm.Realm;
import rpg.realm.RealmManager;

public class Account {
  private static Realm STARTING_REALM = RealmManager.getRealmById(0);
  private static Vector3 STARTING_POS = Vector3.ZERO;

  public final int id;
  public final String username;
  public final String password;

  private Realm realm;
  private Motion motion;

  public Account(String username, String password) {
    this.id = AccountManager.getNextId();
    this.username = username;
    this.password = password;
    realm = STARTING_REALM;
    motion = new StillMotion(STARTING_POS);
    AccountManager.register(this);
  }

  public Realm getRealm() {
    return realm;
  }

  public Vector3 getPos() {
    return motion.getPos();
  }
}
