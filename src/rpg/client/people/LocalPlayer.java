package rpg.client.people;

import rpg.game.CombatClass;
import rpg.game.MotionState;

public class LocalPlayer extends Player {
  private MotionState motionState;

  protected LocalPlayer(CombatClass combatClass) {
    super(combatClass);
  }

  @Override public MotionState getMotionState() {
    return motionState;
  }
}
