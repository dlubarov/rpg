package rpg.client.people;

import org.lwjgl.input.Keyboard;
import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.net.ToServerMessageSink;
import rpg.net.msg.c2s.HereIAmMessage;
import rpg.util.math.Vector3;

public final class LocalPlayer extends Player {
  private static final double MAX_UPDATES_PER_SEC = 3;
  private static final double MAX_SERVER_VIEW_ERROR = 2;
  private static final double FRICTION = 0.01;

  private MotionState motionState;
  private MotionState serverView;
  private double serverUpdatedAt = 0;

  public LocalPlayer(int id, CombatClass combatClass, MotionState motionState) {
    super(id, combatClass);
    serverView = this.motionState = motionState;
  }

  @Override public MotionState getMotionState() {
    return motionState;
  }

  public void logic() {
    // FIXME: logic() methods should take time deltas.
    double forward = 0, left = 0;
    if (Keyboard.isKeyDown(Keyboard.KEY_W))
      forward += getForwardAcceleration();
    if (Keyboard.isKeyDown(Keyboard.KEY_S))
      forward -= getForwardAcceleration();
    if (Keyboard.isKeyDown(Keyboard.KEY_A))
      left += getStrafeAcceleration();
    if (Keyboard.isKeyDown(Keyboard.KEY_D))
      left -= getStrafeAcceleration();

    Vector3 friction = motionState.velocity.neg().limitNorm(FRICTION);
    Vector3 acceleration = dirForward().scaled(forward).plus(dirLeft().scaled(left)).plus(friction);
    updateMotion(acceleration);

    notifyServerOfMotion();
  }

  private void notifyServerOfMotion() {
    double t = System.currentTimeMillis() * 1e-3;
    boolean bigError = serverView.errorComparedTo(motionState) > MAX_SERVER_VIEW_ERROR;
    boolean tooManyUpdates = t - serverUpdatedAt < 1 / MAX_UPDATES_PER_SEC;
    if (bigError && !tooManyUpdates) {
      HereIAmMessage msg = new HereIAmMessage(motionState);
      ToServerMessageSink.singleton.sendWithoutConfirmation(msg);
      serverView = motionState;
      serverUpdatedAt = t;
    }
  }

  private void updateMotion(Vector3 acceleration) {
    Vector3 newVelocity = motionState.velocity.plus(acceleration).limitNorm(getMaxVelocity());
    Vector3 newPosition = motionState.position.plus(newVelocity);
    motionState = motionState.withVelocity(newVelocity).withPosition(newPosition);
  }

  private double getForwardAcceleration() {
    return 0.1;
  }

  private double getStrafeAcceleration() {
    return 0.1;
  }

  private double getMaxVelocity() {
    return 1;
  }

  private Vector3 dirForward() {
    return motionState.withPitch(0).getDirectionVector();
  }

  private Vector3 dirLeft() {
    return motionState.withPitch(0).withYaw(motionState.yaw + Math.PI / 2).getDirectionVector();
  }
}
