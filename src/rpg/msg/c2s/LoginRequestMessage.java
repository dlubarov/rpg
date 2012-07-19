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
public class LoginRequestMessage extends Message {
  public final String usernameOrEmail, password;
  public final Byte[] version;

  public LoginRequestMessage(String usernameOrEmail, String password, Byte[] version) {
    super(MessageType.LOGIN_REQUEST, serializer);
    this.usernameOrEmail = usernameOrEmail;
    this.password = password;
    this.version = version;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("usernameOrEmail", usernameOrEmail)
        .append("password", password)
        .append("version", ArrayUtil.implode('.', version))
        .toString();
  }

  public static final Serializer<LoginRequestMessage> serializer =
      new Serializer<LoginRequestMessage>() {
    @Override
    public void serialize(LoginRequestMessage msg, ByteSink sink) {
      StringSerializer.singleton.serialize(msg.usernameOrEmail, sink);
      StringSerializer.singleton.serialize(msg.password, sink);
      ArraySerializer.byteArraySerializer.serialize(msg.version, sink);
    }

    @Override
    public LoginRequestMessage deserialize(ByteSource source) {
      return new LoginRequestMessage(
          StringSerializer.singleton.deserialize(source),
          StringSerializer.singleton.deserialize(source),
          ArraySerializer.byteArraySerializer.deserialize(source));
    }
  };
}
