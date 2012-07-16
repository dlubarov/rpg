package rpg.server;

import rpg.math.Vector3;
import rpg.util.ToStringBuilder;

public final class MotionSnapshot {
  public final Vector3 position;
  public final Vector3 velocity;
  public final Vector3 direction;

  public MotionSnapshot(Vector3 position, Vector3 velocity, Vector3 direction) {
    this.position = position;
    this.velocity = velocity;
    this.direction = direction;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("position", position)
        .append("velocity", velocity)
        .append("direction", direction)
        .toString();
  }
}
