package rpg.msg.s2c;

import java.util.List;
import rpg.core.CharacterSummary;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.ListSerializer;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

public class CharacterInfoMessage extends Message {
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
