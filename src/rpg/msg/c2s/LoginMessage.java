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
 * Requests a login.
 */
public class LoginMessage extends Message {
  public final String email, password;
  public final List<Byte> version;

  public LoginMessage(String email, String password, List<Byte> version) {
    super(MessageType.LOGIN_REQUEST, serializer);
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

  public static final Serializer<LoginMessage> serializer =
      new Serializer<LoginMessage>() {
        @Override
        public void serialize(LoginMessage msg, ByteSink sink) {
          StringSerializer.singleton.serialize(msg.email, sink);
          StringSerializer.singleton.serialize(msg.password, sink);
          ListSerializer.byteListSerializer.serialize(msg.version, sink);
        }

        @Override
        public LoginMessage deserialize(ByteSource source) {
          return new LoginMessage(
              StringSerializer.singleton.deserialize(source),
              StringSerializer.singleton.deserialize(source),
              ListSerializer.byteListSerializer.deserialize(source));
        }
      };
}
