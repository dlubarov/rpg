package rpg.game;

import rpg.util.ToStringBuilder;
import rpg.util.math.Vector3;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.Serializer;
import rpg.util.serialization.Vector3Serializer;

public final class MotionState {
  public final Vector3 position, velocity, direction;

  public MotionState(Vector3 position, Vector3 velocity, Vector3 direction) {
    this.position = position;
    this.velocity = velocity;
    this.direction = direction;
  }

  public MotionState setPosition(Vector3 position) {
    return new MotionState(position, velocity, direction);
  }

  public MotionState setVelocity(Vector3 velocity) {
    return new MotionState(position, velocity, direction);
  }

  public MotionState setDirection(Vector3 direction) {
    return new MotionState(position, velocity, direction);
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("position", position)
        .append("velocity", velocity)
        .append("direction", direction)
        .toString();
  }

  public static final Serializer<MotionState> serializer = new Serializer<MotionState>() {
    @Override public void serialize(MotionState motionState, ByteSink sink) {
      Vector3Serializer.singleton.serialize(motionState.position, sink);
      Vector3Serializer.singleton.serialize(motionState.velocity, sink);
      Vector3Serializer.singleton.serialize(motionState.direction, sink);
    }

    @Override public MotionState deserialize(ByteSource source) {
      return new MotionState(
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source));
    }
  };
}
