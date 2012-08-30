package rpg.net.msg.c2s;

import rpg.game.CombatClass;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;
import rpg.util.serialization.StringSerializer;

public class NewCharacterMessage extends ClientToServerMessage {
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
        @Override public void serialize(NewCharacterMessage msg, ByteSink sink) {
          StringSerializer.singleton.serialize(msg.characterName, sink);
          IntegerSerializer.singleton.serialize(msg.combatClass.ordinal(), sink);
        }

        @Override public NewCharacterMessage deserialize(ByteSource source) {
          return new NewCharacterMessage(
              StringSerializer.singleton.deserialize(source),
              CombatClass.values()[IntegerSerializer.singleton.deserialize(source)]);
        }
      };
}
