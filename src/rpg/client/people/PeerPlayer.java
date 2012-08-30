package rpg.client.people;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public final class PeerPlayer extends Player {
  private MotionState motionSnapshot;

  public PeerPlayer(int id, CombatClass combatClass) {
    super(id, combatClass);
  }

  @Override public MotionState getMotionState() {
    // TODO: dead reckoning
    return motionSnapshot;
  }
}
