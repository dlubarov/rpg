package rpg.client.people;

import org.lwjgl.input.Keyboard;
import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.util.math.Vector3;

public final class LocalPlayer extends Player {
  private static final double FORWARD_SPEED = 1, STRAFE_SPEED = 1;

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
      forward += FORWARD_SPEED;
    if (Keyboard.isKeyDown(Keyboard.KEY_S))
      forward -= FORWARD_SPEED;
    if (Keyboard.isKeyDown(Keyboard.KEY_A))
      left += STRAFE_SPEED;
    if (Keyboard.isKeyDown(Keyboard.KEY_D))
      left -= STRAFE_SPEED;
    Vector3 acceleration = dirForward().scaled(forward).plus(dirLeft().scaled(left));
    motionState = motionState.withVelocity(motionState.velocity.plus(acceleration));
    motionState = motionState.withPosition(motionState.position.plus(motionState.velocity));
  }

  private Vector3 dirForward() {
    return motionState.withPitch(0).getDirectionVector();
  }

  private Vector3 dirLeft() {
    return motionState.withPitch(0).withYaw(motionState.yaw + Math.PI / 2).getDirectionVector();
  }
}
