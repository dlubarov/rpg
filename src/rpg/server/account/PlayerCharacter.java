package rpg.server.account;

import java.util.concurrent.atomic.AtomicInteger;
import rpg.game.CombatClass;
import rpg.game.Levels;
import rpg.game.MotionState;
import rpg.game.Outfit;
import rpg.game.PlayerSkills;
import rpg.game.PlayerStats;
import rpg.game.item.Coin;
import rpg.game.item.Inventory;
import rpg.game.realm.Realm;
import rpg.game.realm.RealmManager;
import rpg.util.Logger;
import rpg.util.Timing;
import rpg.util.ToStringBuilder;
import rpg.util.math.Vector3;

public class PlayerCharacter {
  private static final Realm STARTING_REALM = RealmManager.getRealmByID(0);
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
  private MotionState motionSnapshot;
  private double motionSnapshotUpdatedAt = Timing.currentTime();
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
    motionSnapshot = new MotionState(STARTING_POS, Vector3.ZERO, 0, 0);
    outfit = new Outfit();

    AccountManager.register(this);
    owner.addCharacter(this);
    Logger.info("New character created: %s.", this);
  }

  public int getLevel() {
    return Levels.experienceToLevel(experience);
  }

  public Realm getRealm() {
    return realm;
  }

  public MotionState getMotionSnapshot() {
    return motionSnapshot;
  }

  public MotionState getExtrapolatedMotionState() {
    return motionSnapshot.extrapolate(Timing.currentTime() - motionSnapshotUpdatedAt);
  }

  public void setMotionSnapshot(MotionState motionSnapshot) {
    this.motionSnapshot = motionSnapshot;
    motionSnapshotUpdatedAt = Timing.currentTime();
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("owner", owner.email)
        .append("combatClass", combatClass)
        .append("realm", realm)
        .append("motionSnapshot", motionSnapshot)
        .append("motionSnapshotUpdatedAt", motionSnapshotUpdatedAt)
        .toString();
  }
}
