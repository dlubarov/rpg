package rpg.client.people;

import rpg.client.gfx.obj.HumanRenderer;
import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.net.msg.s2c.PeerIntroductionMessage;
import rpg.util.Timing;
import rpg.util.math.Vector3;

public final class PeerPlayer extends Player {
  private static final double MAX_UPDATE_SPEED = 6.7;

  private MotionState motionSnapshot;
  private double motionSnapshotTime;

  private Vector3 lastDisplayedPosition;
  private double lastDisplayedAt;

  public PeerPlayer(int id, String characterName,
      CombatClass combatClass, MotionState motionState) {
    super(id, characterName, combatClass);
    setMotionSnapshot(motionState);

    lastDisplayedPosition = motionState.position;
    lastDisplayedAt = Timing.currentTime();
  }

  public PeerPlayer(PeerIntroductionMessage.Part introduction) {
    this(introduction.id, introduction.characterName,
        introduction.combatClass, introduction.motionState);
  }

  public void setMotionSnapshot(MotionState motionSnapshot) {
    this.motionSnapshot = motionSnapshot;
    motionSnapshotTime = Timing.currentTime();
  }

  @Override public MotionState getMotionState() {
    double dt = Timing.currentTime() - lastDisplayedAt;
    double maxUpdateDistance = MAX_UPDATE_SPEED * dt;
    Vector3 ideal = predictCurrentPosition();
    Vector3 pull = ideal.minus(lastDisplayedPosition).limitNorm(maxUpdateDistance);
    Vector3 smoothed = ideal.plus(pull);
    return motionSnapshot.withPosition(smoothed);
  }

  private Vector3 predictCurrentPosition() {
    double dt = Timing.currentTime() - motionSnapshotTime;
    return motionSnapshot.position.plus(motionSnapshot.velocity.scaled(dt));
  }

  public void render() {
    new HumanRenderer(getHumanShape()).render();
    lastDisplayedPosition = getPos();
    lastDisplayedAt = Timing.currentTime();
  }
}
