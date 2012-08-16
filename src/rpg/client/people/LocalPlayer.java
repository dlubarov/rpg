package rpg.client.people;

import rpg.core.CombatClass;
import rpg.core.MotionState;

public class LocalPlayer extends Player {
  private MotionState motionState;

  protected LocalPlayer(CombatClass combatClass) {
    super(combatClass);
  }

  @Override public MotionState getMotionState() {
    return motionState;
  }
}
