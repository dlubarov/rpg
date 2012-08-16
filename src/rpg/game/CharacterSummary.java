package rpg.game;

import rpg.server.PlayerCharacter;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;
import rpg.util.serialization.StringSerializer;

public class CharacterSummary {
  public final int id;
  public final String name;
  public final CombatClass combatClass;
  public final int level;

  public CharacterSummary(int id, String name, CombatClass combatClass, int level) {
    this.id = id;
    this.name = name;
    this.combatClass = combatClass;
    this.level = level;
  }

  public CharacterSummary(PlayerCharacter character) {
    this.id = character.id;
    this.name = character.name;
    this.combatClass = character.combatClass;
    this.level = character.getLevel();
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("combatClass", combatClass)
        .append("level", level)
        .toString();
  }

  public static final Serializer<CharacterSummary> serializer =
      new Serializer<CharacterSummary>() {
        @Override public void serialize(CharacterSummary character, ByteSink sink) {
          IntegerSerializer.singleton.serialize(character.id, sink);
          StringSerializer.singleton.serialize(character.name, sink);
          IntegerSerializer.singleton.serialize(character.combatClass.ordinal(), sink);
          IntegerSerializer.singleton.serialize(character.level, sink);
        }

        @Override public CharacterSummary deserialize(ByteSource source) {
          return new CharacterSummary(
              IntegerSerializer.singleton.deserialize(source),
              StringSerializer.singleton.deserialize(source),
              CombatClass.values()[IntegerSerializer.singleton.deserialize(source)],
              IntegerSerializer.singleton.deserialize(source));
        }
      };
}
