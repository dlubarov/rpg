package rpg.msg.s2c;

import java.util.List;
import rpg.math.Vector3;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.ListSerializer;
import rpg.serialization.Serializer;
import rpg.serialization.StringSerializer;
import rpg.serialization.Vector3Serializer;
import rpg.util.ToStringBuilder;

/**
 * A {@link Message} informing the client of one or more nearby peers which the client was not
 * previously aware of.
 */
public class PeerIntroductionMessage extends Message {
  public final List<Part> parts;

  public PeerIntroductionMessage(List<Part> parts) {
    super(MessageType.PEER_INTRODUCTION, serializer);
    this.parts = parts;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("parts", parts)
        .toString();
  }

  public static final Serializer<PeerIntroductionMessage> serializer =
      new Serializer<PeerIntroductionMessage>() {
        private final Serializer<List<Part>> listSerializer =
            new ListSerializer<Part>(partSerializer);

        @Override
        public void serialize(PeerIntroductionMessage msg, ByteSink sink) {
          listSerializer.serialize(msg.parts, sink);
        }

        @Override
        public PeerIntroductionMessage deserialize(ByteSource source) {
          return new PeerIntroductionMessage(listSerializer.deserialize(source));
        }
      };

  private static final Serializer<Part> partSerializer = new Serializer<Part>() {
    @Override
    public void serialize(Part part, ByteSink sink) {
      IntegerSerializer.singleton.serialize(part.id, sink);
      StringSerializer.singleton.serialize(part.username, sink);
      Vector3Serializer.singleton.serialize(part.position, sink);
      Vector3Serializer.singleton.serialize(part.velocity, sink);
      Vector3Serializer.singleton.serialize(part.direction, sink);
    }

    @Override
    public Part deserialize(ByteSource source) {
      return new Part(
          IntegerSerializer.singleton.deserialize(source),
          StringSerializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source));
    }
  };

  public static class Part {
    public final Integer id;
    public final String username;
    public final Vector3 position, velocity, direction;

    public Part(int id, String username, Vector3 position, Vector3 velocity, Vector3 direction) {
      this.id = id;
      this.username = username;
      this.position = position;
      this.velocity = velocity;
      this.direction = direction;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("id", id)
          .append("username", username)
          .append("position", position)
          .append("velocity", velocity)
          .append("direction", direction)
          .toString();
    }
  }
}
