package rpg.client.people;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.util.math.Vector3;

public abstract class Player extends Person {
  public final int id;
  public final CombatClass combatClass;

  protected Player(int id, CombatClass combatClass) {
    this.id = id;
    this.combatClass = combatClass;
  }

  public abstract MotionState getMotionState();

  @Override public final Vector3 getPos() {
    return getMotionState().position;
  }

  @Override protected final double getHeight() {
    return 1.8;
  }

  @Override protected final double getRadius() {
    return 0.3;
  }
}
