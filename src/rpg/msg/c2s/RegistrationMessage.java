package rpg.msg.c2s;

import java.util.List;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.ListSerializer;
import rpg.serialization.Serializer;
import rpg.serialization.StringSerializer;
import rpg.util.ToStringBuilder;

/**
 * Requests the creation of a new account.
 */
public class RegistrationMessage extends Message {
  public final String email, password;
  public final List<Byte> version;

  public RegistrationMessage(String email, String password, List<Byte> version) {
    super(MessageType.REGISTRATION_REQUEST, serializer);
    this.email = email;
    this.password = password;
    this.version = version;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("email", email)
        .append("password", password)
        .append("version", version) // TODO: CollectionUtil.implode
        .toString();
  }

  public static final Serializer<RegistrationMessage> serializer =
      new Serializer<RegistrationMessage>() {
    @Override
    public void serialize(RegistrationMessage msg, ByteSink sink) {
      StringSerializer.singleton.serialize(msg.email, sink);
      StringSerializer.singleton.serialize(msg.password, sink);
      ListSerializer.byteListSerializer.serialize(msg.version, sink);
    }

    @Override
    public RegistrationMessage deserialize(ByteSource source) {
      return new RegistrationMessage(
          StringSerializer.singleton.deserialize(source),
          StringSerializer.singleton.deserialize(source),
          ListSerializer.byteListSerializer.deserialize(source));
    }
  };
}
