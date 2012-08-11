package rpg.core;

import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.serialization.StringSerializer;
import rpg.server.PlayerCharacter;
import rpg.util.ToStringBuilder;

public class CharacterSummary {
  public final int characterID;
  public final String name;
  public final CombatClass combatClass;
  public final int level;

  public CharacterSummary(int characterID, String name, CombatClass combatClass, int level) {
    this.characterID = characterID;
    this.name = name;
    this.combatClass = combatClass;
    this.level = level;
  }

  public CharacterSummary(PlayerCharacter character) {
    this.characterID = character.id;
    this.name = character.name;
    this.combatClass = character.combatClass;
    this.level = character.getLevel();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("characterID", characterID)
        .append("name", name)
        .append("combatClass", combatClass)
        .append("level", level)
        .toString();
  }

  public static final Serializer<CharacterSummary> serializer =
      new Serializer<CharacterSummary>() {
        @Override
        public void serialize(CharacterSummary character, ByteSink sink) {
          IntegerSerializer.singleton.serialize(character.characterID, sink);
          StringSerializer.singleton.serialize(character.name, sink);
          IntegerSerializer.singleton.serialize(character.combatClass.ordinal(), sink);
          IntegerSerializer.singleton.serialize(character.level, sink);
        }

        @Override
        public CharacterSummary deserialize(ByteSource source) {
          return new CharacterSummary(
              IntegerSerializer.singleton.deserialize(source),
              StringSerializer.singleton.deserialize(source),
              CombatClass.values()[IntegerSerializer.singleton.deserialize(source)],
              IntegerSerializer.singleton.deserialize(source));
        }
      };
}
