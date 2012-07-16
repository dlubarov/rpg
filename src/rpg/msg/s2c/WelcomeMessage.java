package rpg.msg.s2c;

import rpg.math.Vector3;
import rpg.msg.Message;
import rpg.realm.Realm;
import rpg.realm.RealmManager;
import rpg.ser.ByteSink;
import rpg.ser.ByteSource;
import rpg.ser.IntegerSerializer;
import rpg.ser.Serializer;
import rpg.ser.Vector3Serializer;
import rpg.util.ToStringBuilder;

/**
 * Welcomes a client who just signed in. Informs the client of his current realm and position.
 */
public class WelcomeMessage extends Message {
  public final Integer id;
  public final Realm realm;
  public final Vector3 position, velocity, direction;

  public WelcomeMessage(Integer id, Realm realm, Vector3 position, Vector3 velocity,
      Vector3 direction) {
    this.id = id;
    this.realm = realm;
    this.position = position;
    this.velocity = velocity;
    this.direction = direction;
  }

  public static final Serializer<WelcomeMessage> serializer = new Serializer<WelcomeMessage>() {
    @Override
    public void serialize(WelcomeMessage msg, ByteSink sink) {
      IntegerSerializer.singleton.serialize(msg.id, sink);
      IntegerSerializer.singleton.serialize(msg.realm.id, sink);
      Vector3Serializer.singleton.serialize(msg.position, sink);
      Vector3Serializer.singleton.serialize(msg.velocity, sink);
      Vector3Serializer.singleton.serialize(msg.direction, sink);
    }

    @Override
    public WelcomeMessage deserialize(ByteSource source) {
      return new WelcomeMessage(
          IntegerSerializer.singleton.deserialize(source),
          RealmManager.getRealmById(IntegerSerializer.singleton.deserialize(source)),
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source),
          Vector3Serializer.singleton.deserialize(source)
      );
    }
  };

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("realm", realm)
        .append("position", position)
        .append("velocity", velocity)
        .append("direction", direction)
        .toString();
  }
}
