package rpg.core;

import rpg.math.Vector3;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.Serializer;
import rpg.serialization.Vector3Serializer;
import rpg.util.ToStringBuilder;

public class MotionState {
  public final Vector3 position, velocity, direction;

  public MotionState(Vector3 position, Vector3 velocity, Vector3 direction) {
    this.position = position;
    this.velocity = velocity;
    this.direction = direction;
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
