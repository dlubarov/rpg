package rpg.game;

import rpg.util.ToStringBuilder;
import rpg.util.math.Vector3;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.DoubleSerializer;
import rpg.util.serialization.Serializer;
import rpg.util.serialization.Vector3Serializer;

public final class MotionState {
  public final Vector3 position, velocity;
  public final double yaw, pitch;

  public MotionState(Vector3 position, Vector3 velocity, double yaw, double pitch) {
    this.position = position;
    this.velocity = velocity;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public Vector3 getDirectionVector() {
    double xz = Math.cos(pitch),
           y = Math.sin(pitch),
           x = xz * Math.cos(yaw),
           z = xz * -Math.sin(yaw);
    return new Vector3(x, y, z);
  }

  public MotionState withPosition(Vector3 position) {
    return new MotionState(position, velocity, yaw, pitch);
  }

  public MotionState withVelocity(Vector3 velocity) {
    return new MotionState(position, velocity, yaw, pitch);
  }

  public MotionState withYaw(double yaw) {
    return new MotionState(position, velocity, yaw, pitch);
  }

  public MotionState withPitch(double pitch) {
    return new MotionState(position, velocity, yaw, pitch);
  }

  public double errorComparedTo(MotionState that) {
    return errorFor(
        position.euclideanDistanceTo(that.position),
        velocity.euclideanDistanceTo(that.velocity),
        getDirectionVector().euclideanDistanceTo(that.getDirectionVector()));
  }

  public MotionState extrapolate(double dt) {
    // TODO: This was too extreme. Revisit later and consider more mild extrapolation.
    //return withPosition(position.plus(velocity.scaled(dt)));
    return this;
  }

  private static double errorFor(double posErr, double velErr, double dirErr) {
    // TODO: Optimize error function.
    return posErr + velErr + dirErr;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("position", position)
        .append("velocity", velocity)
        .append("yaw", yaw)
        .append("pitch", pitch)
        .toString();
  }

  public static final Serializer<MotionState> serializer = new Serializer<MotionState>() {
    @Override public void serialize(MotionState motionState, ByteSink sink) {
      Vector3Serializer.singleton.serialize(motionState.position, sink);
      Vector3Serializer.singleton.serialize(motionState.velocity, sink);
      DoubleSerializer.singleton.serialize(motionState.yaw, sink);
      DoubleSerializer.singleton.serialize(motionState.pitch, sink);
    }

    @Override public MotionState deserialize(ByteSource source) {
      return new MotionState(
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source),
          DoubleSerializer.singleton.deserialize(source),
          DoubleSerializer.singleton.deserialize(source));
    }
  };
}
