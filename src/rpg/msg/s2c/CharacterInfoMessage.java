package rpg.msg.s2c;

import java.util.Arrays;
import rpg.core.CharacterSummary;
import rpg.core.CombatClass;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ArraySerializer;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.serialization.StringSerializer;
import rpg.util.ToStringBuilder;

public class CharacterInfoMessage extends Message {
  public final CharacterSummary[] parts;

  public CharacterInfoMessage(CharacterSummary[] parts) {
    super(MessageType.CHARACTER_INFO, serializer);
    this.parts = parts;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("parts", Arrays.toString(parts))
        .toString();
  }

  public static final Serializer<CharacterInfoMessage> serializer =
      new Serializer<CharacterInfoMessage>() {
    private final Serializer<CharacterSummary[]> arraySerializer =
        new ArraySerializer<CharacterSummary>(partSerializer);

    @Override
    public void serialize(CharacterInfoMessage msg, ByteSink sink) {
      arraySerializer.serialize(msg.parts, sink);
    }

    @Override
    public CharacterInfoMessage deserialize(ByteSource source) {
      return new CharacterInfoMessage(arraySerializer.deserialize(source));
    }
  };

  private static final Serializer<CharacterSummary> partSerializer =
      new Serializer<CharacterSummary>() {
    @Override
    public void serialize(CharacterSummary character, ByteSink sink) {
      StringSerializer.singleton.serialize(character.name, sink);
      IntegerSerializer.singleton.serialize(character.combatClass.ordinal(), sink);
    }

    @Override
    public CharacterSummary deserialize(ByteSource source) {
      return new CharacterSummary(
          StringSerializer.singleton.deserialize(source),
          CombatClass.values()[IntegerSerializer.singleton.deserialize(source)]);
    }
  };
}
