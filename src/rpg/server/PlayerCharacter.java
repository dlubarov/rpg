package rpg.server;

import java.util.concurrent.atomic.AtomicInteger;
import rpg.core.CombatClass;
import rpg.core.Levels;
import rpg.core.MotionState;
import rpg.core.Outfit;
import rpg.core.PlayerSkills;
import rpg.core.PlayerStats;
import rpg.core.item.Coin;
import rpg.core.item.Inventory;
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
  public final Inventory bag, bank;

  private Realm realm;
  private MotionState motionState;
  public final Outfit outfit;

  public PlayerCharacter(String name, Account owner, CombatClass combatClass) {
    this.id = idCounter.getAndIncrement();
    this.name = name;
    this.owner = owner;
    this.combatClass = combatClass;
    experience = 0;
    stats = new PlayerStats();
    skills = new PlayerSkills();
    bag = new Inventory();
    bag.setQuantity(Coin.singleton, 5);
    bank = new Inventory();
    realm = STARTING_REALM;
    motionState = new MotionState(STARTING_POS, Vector3.ZERO, Vector3.UNIT_X);
    outfit = new Outfit();
  }

  public int getLevel() {
    return Levels.experienceToLevel(experience);
  }

  public Realm getRealm() {
    return realm;
  }

  public MotionState getMotionState() {
    return motionState;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("owner", owner.email)
        .append("combatClass", combatClass)
        .append("realm", realm)
        .append("motionState", motionState)
        .toString();
  }
}
