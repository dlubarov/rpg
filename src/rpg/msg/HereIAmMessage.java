package rpg.msg;

import rpg.math.Vector3;
import rpg.ser.ByteSink;
import rpg.ser.ByteSource;
import rpg.ser.Serializer;
import rpg.ser.Vector3Serializer;
import rpg.util.ToStringBuilder;

public class HereIAmMessage extends Message {
  public final Vector3 position, velocity, direction;

  public HereIAmMessage(Vector3 position, Vector3 velocity, Vector3 direction) {
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

  public static final Serializer<HereIAmMessage> serializer =
      new Serializer<HereIAmMessage>() {
    @Override
    public void serialize(HereIAmMessage msg, ByteSink sink) {
      Vector3Serializer.singleton.serialize(msg.position, sink);
      Vector3Serializer.singleton.serialize(msg.velocity, sink);
      Vector3Serializer.singleton.serialize(msg.direction, sink);
    }

    @Override
    public HereIAmMessage deserialize(ByteSource source) {
      return new HereIAmMessage(
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source));
    }
  };
}
