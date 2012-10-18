package rpg.client.people;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.net.ToServerMessageSink;
import rpg.net.msg.c2s.HereIAmMessage;
import rpg.util.Timing;
import rpg.util.math.MathUtil;
import rpg.util.math.Vector3;

public final class LocalPlayer extends Player {
  private static final double MAX_UPDATES_PER_SEC = 3;
  private static final double MAX_TIME_BETWEEN_UPDATES = 2; // 2 seconds
  private static final double MAX_SERVER_VIEW_ERROR = 2;

  private static final double MOUSE_SPEED = 0.01;
  private static final double FRICTION = 6;

  private MotionState motionState;
  private MotionState serverView;
  private double serverUpdatedAt = Timing.currentTime();

  public LocalPlayer(int id, String characterName,
      CombatClass combatClass, MotionState motionState) {
    super(id, characterName, combatClass);
    serverView = this.motionState = motionState;
  }

  @Override public MotionState getMotionState() {
    return motionState;
  }

  public void logic(double dt) {
    rotate();

    double forward = 0, left = 0;
    if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
      forward += getForwardAcceleration();
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
      forward -= getForwardAcceleration();
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
      left += getStrafeAcceleration();
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
      left -= getStrafeAcceleration();
    }

    Vector3 friction = motionState.velocity.neg().scaled(1 / dt).limitNorm(FRICTION);
    Vector3 acceleration = dirForward().scaled(forward).plus(dirLeft().scaled(left)).plus(friction);
    updateMotion(dt, acceleration);
    notifyServerOfMotion();
  }

  private void rotate() {
    double dx = Mouse.getDX() * MOUSE_SPEED,
           dy = Mouse.getDY() * MOUSE_SPEED;
    double newYaw = motionState.yaw - dx,
           newPitch = motionState.pitch + dy;
    newPitch = MathUtil.clamp(newPitch, -Math.PI / 2, Math.PI / 2);
    motionState = motionState.withYaw(newYaw).withPitch(newPitch);
  }

  private void notifyServerOfMotion() {
    double t = Timing.currentTime(), timeSinceUpdate = t - serverUpdatedAt;
    boolean bigError = serverView.errorComparedTo(motionState) > MAX_SERVER_VIEW_ERROR;
    boolean tooManyUpdates = timeSinceUpdate < 1 / MAX_UPDATES_PER_SEC;
    boolean tooFewUpdates = timeSinceUpdate > MAX_TIME_BETWEEN_UPDATES;
    if (bigError && !tooManyUpdates || tooFewUpdates) {
      HereIAmMessage msg = new HereIAmMessage(motionState);
      ToServerMessageSink.singleton.sendWithoutConfirmation(msg);
      serverView = motionState;
      serverUpdatedAt = t;
    }
  }

  private void updateMotion(double dt, Vector3 acceleration) {
    Vector3 newVelocity = motionState.velocity
        .plus(acceleration.scaled(dt))
        .limitNorm(getMaxVelocity());
    Vector3 newPosition = motionState.position
        .plus(newVelocity.scaled(dt));
    motionState = motionState
        .withVelocity(newVelocity)
        .withPosition(newPosition);
  }

  private double getForwardAcceleration() {
    return 50;
  }

  private double getStrafeAcceleration() {
    return 50;
  }

  private double getMaxVelocity() {
    return 4;
  }
}
