package rpg.net.msg.s2c;

import java.util.List;
import rpg.game.MotionState;
import rpg.net.msg.Message;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.ListSerializer;
import rpg.util.serialization.Serializer;

/**
 * A {@link rpg.net.msg.Message} informing the client of one or more nearby peers which the client was not
 * previously aware of.
 */
public class PeerUpdateMessage extends Message {
  public final List<Part> parts;

  public PeerUpdateMessage(List<Part> parts) {
    super(MessageType.PEER_UPDATE, serializer);
    this.parts = parts;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("parts", parts)
        .toString();
  }

  private static final Serializer<Part> partSerializer = new Serializer<Part>() {
    @Override public void serialize(Part part, ByteSink sink) {
      IntegerSerializer.singleton.serialize(part.id, sink);
      MotionState.serializer.serialize(part.motionState, sink);
    }

    @Override public Part deserialize(ByteSource source) {
      return new Part(
          IntegerSerializer.singleton.deserialize(source),
          MotionState.serializer.deserialize(source));
    }
  };

  public static final Serializer<PeerUpdateMessage> serializer =
      new Serializer<PeerUpdateMessage>() {
        private final Serializer<List<Part>> listSerializer =
            new ListSerializer<Part>(partSerializer);

        @Override public void serialize(PeerUpdateMessage msg, ByteSink sink) {
          listSerializer.serialize(msg.parts, sink);
        }

        @Override public PeerUpdateMessage deserialize(ByteSource source) {
          return new PeerUpdateMessage(listSerializer.deserialize(source));
        }
      };

  public static class Part {
    public final Integer id;
    public final MotionState motionState;

    public Part(int id, MotionState motionState) {
      this.id = id;
      this.motionState = motionState;
    }

    @Override public String toString() {
      return new ToStringBuilder(this)
          .append("id", id)
          .append("motionState", motionState)
          .toString();
    }
  }
}
