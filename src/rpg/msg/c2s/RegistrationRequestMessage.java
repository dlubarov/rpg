package rpg.msg.c2s;

import rpg.msg.Message;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.Serializer;
import rpg.serialization.StringSerializer;
import rpg.util.ToStringBuilder;

/**
 * Requests the creation of a new account.
 */
public class RegistrationRequestMessage extends Message {
  public final String username, password;

  public RegistrationRequestMessage(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("username", username)
        .append("password", password)
        .toString();
  }

  public static final Serializer<RegistrationRequestMessage> serializer =
      new Serializer<RegistrationRequestMessage>() {
    @Override
    public void serialize(RegistrationRequestMessage msg, ByteSink sink) {
      StringSerializer.singleton.serialize(msg.username, sink);
      StringSerializer.singleton.serialize(msg.password, sink);
    }

    @Override
    public RegistrationRequestMessage deserialize(ByteSource source) {
      return new RegistrationRequestMessage(
          StringSerializer.singleton.deserialize(source),
          StringSerializer.singleton.deserialize(source));
    }
  };
}
