package rpg.msg.c2s;

import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

public class CharacterSelectedMessage extends Message {
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
