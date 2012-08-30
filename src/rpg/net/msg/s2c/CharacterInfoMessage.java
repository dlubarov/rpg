package rpg.net.msg.s2c;

import java.util.List;
import rpg.game.CharacterSummary;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.ListSerializer;
import rpg.util.serialization.Serializer;

public class CharacterInfoMessage extends ServerToClientMessage {
  public final List<CharacterSummary> parts;

  public CharacterInfoMessage(List<CharacterSummary> parts) {
    super(MessageType.CHARACTER_INFO, serializer);
    this.parts = parts;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("parts", parts)
        .toString();
  }

  public static final Serializer<CharacterInfoMessage> serializer =
      new Serializer<CharacterInfoMessage>() {
        private final Serializer<List<CharacterSummary>> arraySerializer =
            new ListSerializer<CharacterSummary>(CharacterSummary.serializer);

        @Override public void serialize(CharacterInfoMessage msg, ByteSink sink) {
          arraySerializer.serialize(msg.parts, sink);
        }

        @Override public CharacterInfoMessage deserialize(ByteSource source) {
          return new CharacterInfoMessage(arraySerializer.deserialize(source));
        }
      };
}
