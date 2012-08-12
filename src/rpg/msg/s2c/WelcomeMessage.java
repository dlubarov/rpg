package rpg.msg.s2c;

import rpg.math.Vector3;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.realm.Realm;
import rpg.realm.RealmManager;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.serialization.Vector3Serializer;
import rpg.util.ToStringBuilder;

/**
 * Welcomes a client who just signed in. Informs the client of his current realm and position.
 */
public class WelcomeMessage extends Message {
  public final Integer characterID;
  public final Realm realm;
  public final Vector3 position, velocity, direction;

  public WelcomeMessage(Integer characterID, Realm realm, Vector3 position, Vector3 velocity,
      Vector3 direction) {
    super(MessageType.WELCOME, serializer);
    this.characterID = characterID;
    this.realm = realm;
    this.position = position;
    this.velocity = velocity;
    this.direction = direction;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("characterID", characterID)
        .append("realm", realm)
        .append("position", position)
        .append("velocity", velocity)
        .append("direction", direction)
        .toString();
  }

  public static final Serializer<WelcomeMessage> serializer =
      new Serializer<WelcomeMessage>() {
        @Override public void serialize(WelcomeMessage msg, ByteSink sink) {
          IntegerSerializer.singleton.serialize(msg.characterID, sink);
          IntegerSerializer.singleton.serialize(msg.realm.id, sink);
          Vector3Serializer.singleton.serialize(msg.position, sink);
          Vector3Serializer.singleton.serialize(msg.velocity, sink);
          Vector3Serializer.singleton.serialize(msg.direction, sink);
        }

        @Override public WelcomeMessage deserialize(ByteSource source) {
          return new WelcomeMessage(
              IntegerSerializer.singleton.deserialize(source),
              RealmManager.getRealmById(IntegerSerializer.singleton.deserialize(source)),
              Vector3Serializer.singleton.deserialize(source),
              Vector3Serializer.singleton.deserialize(source),
              Vector3Serializer.singleton.deserialize(source)
          );
        }
      };
}
