package rpg.server;

import rpg.core.CombatClass;
import rpg.math.Vector3;
import rpg.realm.Realm;
import rpg.realm.RealmManager;
import rpg.util.ToStringBuilder;

public class PlayerCharacter {
  private static final Realm STARTING_REALM = RealmManager.getRealmById(0);
  private static final Vector3 STARTING_POS = Vector3.ZERO;

  public final String name;
  public final Account owner;
  public final CombatClass combatClass;

  private Realm realm;
  private Vector3 position, velocity, direction;

  public PlayerCharacter(String name, Account owner, CombatClass combatClass) {
    this.name = name;
    this.owner = owner;
    this.combatClass = combatClass;

    realm = STARTING_REALM;
    position = STARTING_POS;
    velocity = Vector3.ZERO;
    direction = Vector3.UNIT_X;
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

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("owner", owner.email)
        .append("combatClass", combatClass)
        .append("realm", realm)
        .append("position", position)
        .append("velocity", velocity)
        .append("direction", direction)
        .toString();
  }
}
