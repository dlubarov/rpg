package rpg.net.msg.s2c;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.game.realm.Realm;
import rpg.game.realm.RealmManager;
import rpg.net.msg.Message;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;

/**
 * Welcomes a client who just signed in. Informs the client of his current realm and position.
 */
public class WelcomeMessage extends Message {
  public final Integer characterID;
  public final CombatClass combatClass;
  public final Realm realm;
  public final MotionState motionState;

  public WelcomeMessage(Integer characterID, CombatClass combatClass, Realm realm,
      MotionState motionState) {
    super(MessageType.WELCOME, serializer);
    this.characterID = characterID;
    this.combatClass = combatClass;
    this.realm = realm;
    this.motionState = motionState;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("characterID", characterID)
        .append("combatClass", combatClass)
        .append("realm", realm)
        .append("motionState", motionState)
        .toString();
  }

  public static final Serializer<WelcomeMessage> serializer = new Serializer<WelcomeMessage>() {
    @Override public void serialize(WelcomeMessage msg, ByteSink sink) {
      IntegerSerializer.singleton.serialize(msg.characterID, sink);
      IntegerSerializer.singleton.serialize(msg.combatClass.ordinal(), sink);
      IntegerSerializer.singleton.serialize(msg.realm.id, sink);
      MotionState.serializer.serialize(msg.motionState, sink);
    }

    @Override public WelcomeMessage deserialize(ByteSource source) {
      return new WelcomeMessage(
          IntegerSerializer.singleton.deserialize(source),
          CombatClass.values()[IntegerSerializer.singleton.deserialize(source)],
          RealmManager.getRealmById(IntegerSerializer.singleton.deserialize(source)),
          MotionState.serializer.deserialize(source));
    }
  };
}
