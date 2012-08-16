package rpg.client.people;

import org.lwjgl.input.Keyboard;
import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.util.Logger;
import rpg.util.math.Vector3;

public final class LocalPlayer extends Player {
  private static final double FRICTION = 0.01;

  private MotionState motionState;

  public LocalPlayer(int id, CombatClass combatClass, MotionState motionState) {
    super(id, combatClass);
    this.motionState = motionState;
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
  }

  private void updateMotion(Vector3 acceleration) {
    Logger.info(dirLeft().toString());
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
