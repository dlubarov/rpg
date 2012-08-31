package rpg.client.people;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.util.Timing;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public final class PeerPlayer extends Player {
  private MotionState motionSnapshot;
  private double motionSnapshotTime;

  public PeerPlayer(int id, String characterName,
      CombatClass combatClass, MotionState motionState) {
    super(id, characterName, combatClass);
    setMotionSnapshot(motionState);
  }

  public void setMotionSnapshot(MotionState motionSnapshot) {
    this.motionSnapshot = motionSnapshot;
    motionSnapshotTime = Timing.currentTime();
  }

  @Override public MotionState getMotionState() {
    // TODO: dead reckoning
    return motionSnapshot;
  }
}
