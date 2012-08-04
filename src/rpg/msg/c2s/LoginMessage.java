package rpg.msg.c2s;

import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ArraySerializer;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.Serializer;
import rpg.serialization.StringSerializer;
import rpg.util.ArrayUtil;
import rpg.util.ToStringBuilder;

/**
 * Requests a login.
 */
public class LoginMessage extends Message {
  public final String email, password;
  public final Byte[] version;

  public LoginMessage(String email, String password, Byte[] version) {
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
        .append("version", ArrayUtil.implode('.', version))
        .toString();
  }

  public static final Serializer<LoginMessage> serializer =
      new Serializer<LoginMessage>() {
    @Override
    public void serialize(LoginMessage msg, ByteSink sink) {
      StringSerializer.singleton.serialize(msg.email, sink);
      StringSerializer.singleton.serialize(msg.password, sink);
      ArraySerializer.byteArraySerializer.serialize(msg.version, sink);
    }

    @Override
    public LoginMessage deserialize(ByteSource source) {
      return new LoginMessage(
          StringSerializer.singleton.deserialize(source),
          StringSerializer.singleton.deserialize(source),
          ArraySerializer.byteArraySerializer.deserialize(source));
    }
  };
}
