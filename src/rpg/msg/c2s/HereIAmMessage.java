package rpg.msg.c2s;

import rpg.math.Vector3;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.Serializer;
import rpg.serialization.Vector3Serializer;
import rpg.util.ToStringBuilder;

/**
 * Informs the server of the player's latest position.
 */
public class HereIAmMessage extends Message {
  public final Vector3 position, velocity, direction;

  public HereIAmMessage(Vector3 position, Vector3 velocity, Vector3 direction) {
    super(MessageType.HERE_I_AM, serializer);
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

  public static final Serializer<HereIAmMessage> serializer = new Serializer<HereIAmMessage>() {
    @Override public void serialize(HereIAmMessage msg, ByteSink sink) {
      Vector3Serializer.singleton.serialize(msg.position, sink);
      Vector3Serializer.singleton.serialize(msg.velocity, sink);
      Vector3Serializer.singleton.serialize(msg.direction, sink);
    }

    @Override public HereIAmMessage deserialize(ByteSource source) {
      return new HereIAmMessage(
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source));
    }
  };
}
