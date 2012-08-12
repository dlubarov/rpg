package rpg.server;

import java.util.concurrent.atomic.AtomicInteger;
import rpg.core.CombatClass;
import rpg.core.Levels;
import rpg.core.Outfit;
import rpg.core.PlayerSkills;
import rpg.core.PlayerStats;
import rpg.math.Vector3;
import rpg.realm.Realm;
import rpg.realm.RealmManager;
import rpg.util.ToStringBuilder;

public class PlayerCharacter {
  private static final Realm STARTING_REALM = RealmManager.getRealmById(0);
  private static final Vector3 STARTING_POS = Vector3.ZERO;

  private static final AtomicInteger idCounter = new AtomicInteger();

  public final int id;
  public final String name;
  public final Account owner;
  public final CombatClass combatClass;
  public final int experience;
  public final PlayerStats stats;
  public final PlayerSkills skills;

  private Realm realm;
  private Vector3 position, velocity, direction;
  public final Outfit outfit;

  public PlayerCharacter(String name, Account owner, CombatClass combatClass) {
    this.id = idCounter.getAndIncrement();
    this.name = name;
    this.owner = owner;
    this.combatClass = combatClass;
    experience = 0;
    stats = new PlayerStats();
    skills = new PlayerSkills();
    realm = STARTING_REALM;
    position = STARTING_POS;
    velocity = Vector3.ZERO;
    direction = Vector3.UNIT_X;
    outfit = new Outfit();
  }

  public int getLevel() {
    return Levels.experienceToLevel(experience);
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

  @Override public String toString() {
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
