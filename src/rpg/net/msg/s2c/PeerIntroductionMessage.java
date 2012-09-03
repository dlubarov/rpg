package rpg.net.msg.s2c;

import java.util.List;
import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.net.msg.Message;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.ListSerializer;
import rpg.util.serialization.Serializer;
import rpg.util.serialization.StringSerializer;

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

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("parts", parts)
        .toString();
  }

  private static final Serializer<Part> partSerializer = new Serializer<Part>() {
    @Override public void serialize(Part part, ByteSink sink) {
      IntegerSerializer.singleton.serialize(part.id, sink);
      StringSerializer.singleton.serialize(part.characterName, sink);
      IntegerSerializer.singleton.serialize(part.combatClass.ordinal(), sink);
      MotionState.serializer.serialize(part.motionState, sink);
    }

    @Override public Part deserialize(ByteSource source) {
      return new Part(
          IntegerSerializer.singleton.deserialize(source),
          StringSerializer.singleton.deserialize(source),
          CombatClass.values()[IntegerSerializer.singleton.deserialize(source)],
          MotionState.serializer.deserialize(source));
    }
  };

  public static final Serializer<PeerIntroductionMessage> serializer =
      new Serializer<PeerIntroductionMessage>() {
        private final Serializer<List<Part>> listSerializer =
            new ListSerializer<Part>(partSerializer);

        @Override public void serialize(PeerIntroductionMessage msg, ByteSink sink) {
          listSerializer.serialize(msg.parts, sink);
        }

        @Override public PeerIntroductionMessage deserialize(ByteSource source) {
          return new PeerIntroductionMessage(listSerializer.deserialize(source));
        }
      };

  public static class Part {
    public final Integer id;
    public final String characterName;
    public final CombatClass combatClass;
    public final MotionState motionState;

    public Part(int id, String characterName, CombatClass combatClass, MotionState motionState) {
      this.id = id;
      this.characterName = characterName;
      this.combatClass = combatClass;
      this.motionState = motionState;
    }

    @Override public String toString() {
      return new ToStringBuilder(this)
          .append("id", id)
          .append("characterName", characterName)
          .append("motionState", motionState)
          .toString();
    }
  }
}
