package rpg.msg.s2c;

import rpg.core.MotionState;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.realm.Realm;
import rpg.realm.RealmManager;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

/**
 * Welcomes a client who just signed in. Informs the client of his current realm and position.
 */
public class WelcomeMessage extends Message {
  public final Integer characterID;
  public final Realm realm;
  public final MotionState motionState;

  public WelcomeMessage(Integer characterID, Realm realm, MotionState motionState) {
    super(MessageType.WELCOME, serializer);
    this.characterID = characterID;
    this.realm = realm;
    this.motionState = motionState;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("characterID", characterID)
        .append("realm", realm)
        .append("motionState", motionState)
        .toString();
  }

  public static final Serializer<WelcomeMessage> serializer = new Serializer<WelcomeMessage>() {
    @Override public void serialize(WelcomeMessage msg, ByteSink sink) {
      IntegerSerializer.singleton.serialize(msg.characterID, sink);
      IntegerSerializer.singleton.serialize(msg.realm.id, sink);
      MotionState.serializer.serialize(msg.motionState, sink);
    }

    @Override public WelcomeMessage deserialize(ByteSource source) {
      return new WelcomeMessage(
          IntegerSerializer.singleton.deserialize(source),
          RealmManager.getRealmById(IntegerSerializer.singleton.deserialize(source)),
          MotionState.serializer.deserialize(source));
    }
  };
}
