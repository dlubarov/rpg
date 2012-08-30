package rpg.net.msg.c2s;

import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;

public class CharacterSelectedMessage extends ClientToServerMessage {
  public final int id;

  public CharacterSelectedMessage(int id) {
    super(MessageType.CHARACTER_SELECTED, serializer);
    this.id = id;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .toString();
  }

  public static final Serializer<CharacterSelectedMessage> serializer =
      new Serializer<CharacterSelectedMessage>() {
        @Override public void serialize(CharacterSelectedMessage msg, ByteSink sink) {
          IntegerSerializer.singleton.serialize(msg.id, sink);
        }

        @Override public CharacterSelectedMessage deserialize(ByteSource source) {
          return new CharacterSelectedMessage(IntegerSerializer.singleton.deserialize(source));
        }
      };
}
