package rpg.client.people;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public final class PeerPlayer extends Player {
  protected PeerPlayer(int id, CombatClass combatClass) {
    super(id, combatClass);
  }

  @Override public MotionState getMotionState() {
    // FIXME: Implement this.
    throw new NotImplementedException();
  }
}
