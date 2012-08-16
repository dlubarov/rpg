package rpg.client.people;

import rpg.core.CombatClass;
import rpg.core.MotionState;
import rpg.math.Vector3;

public abstract class Player extends Person {
  public final CombatClass combatClass;

  protected Player(CombatClass combatClass) {
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
