package rpg.net.msg.s2c.peer;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.net.msg.Message;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;
import rpg.util.serialization.StringSerializer;

/**
 * A {@link Message} informing the client of one or more nearby peers which the client was not
 * previously aware of.
 */
public class PeerIntroduction {
  public final Integer id;
  public final String characterName;
  public final CombatClass combatClass;
  public final MotionState motionState;

  public PeerIntroduction(int id, String characterName,
      CombatClass combatClass, MotionState motionState) {
    this.id = id;
    this.characterName = characterName;
    this.combatClass = combatClass;
    this.motionState = motionState;
  }

  public static final Serializer<PeerIntroduction> serializer =
      new Serializer<PeerIntroduction>() {
        @Override public void serialize(PeerIntroduction intro, ByteSink sink) {
          IntegerSerializer.singleton.serialize(intro.id, sink);
          StringSerializer.singleton.serialize(intro.characterName, sink);
          IntegerSerializer.singleton.serialize(intro.combatClass.ordinal(), sink);
          MotionState.serializer.serialize(intro.motionState, sink);
        }

        @Override public PeerIntroduction deserialize(ByteSource source) {
          return new PeerIntroduction(
              IntegerSerializer.singleton.deserialize(source),
              StringSerializer.singleton.deserialize(source),
              CombatClass.values()[IntegerSerializer.singleton.deserialize(source)],
              MotionState.serializer.deserialize(source));
        }
      };

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("characterName", characterName)
        .append("motionState", motionState)
        .toString();
  }
}
