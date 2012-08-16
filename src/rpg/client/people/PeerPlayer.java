package rpg.client.people;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PeerPlayer extends Player {
  protected PeerPlayer(CombatClass combatClass) {
    super(combatClass);
  }

  @Override public MotionState getMotionState() {
    // FIXME: Implement this.
    throw new NotImplementedException();
  }
}
