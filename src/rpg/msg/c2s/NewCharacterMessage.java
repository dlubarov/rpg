package rpg.msg.c2s;

import rpg.core.CombatClass;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.serialization.StringSerializer;
import rpg.util.ToStringBuilder;

public class NewCharacterMessage extends Message {
  public final String characterName;
  public final CombatClass combatClass;

  public NewCharacterMessage(String characterName, CombatClass combatClass) {
    super(MessageType.NEW_CHARACTER, serializer);
    this.characterName = characterName;
    this.combatClass = combatClass;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("characterName", characterName)
        .append("combatClass", combatClass)
        .toString();
  }

  public static final Serializer<NewCharacterMessage> serializer =
      new Serializer<NewCharacterMessage>() {
        @Override
        public void serialize(NewCharacterMessage msg, ByteSink sink) {
          StringSerializer.singleton.serialize(msg.characterName, sink);
          IntegerSerializer.singleton.serialize(msg.combatClass.ordinal(), sink);
        }

        @Override
        public NewCharacterMessage deserialize(ByteSource source) {
          return new NewCharacterMessage(
              StringSerializer.singleton.deserialize(source),
              CombatClass.values()[IntegerSerializer.singleton.deserialize(source)]);
        }
      };
}
