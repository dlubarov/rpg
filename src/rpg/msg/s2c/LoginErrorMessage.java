package rpg.msg.s2c;

import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

/**
 * A {@link rpg.msg.Message} informing the client that their login was declined.
 */
public class LoginErrorMessage extends Message {
  public final Reason reason;

  public LoginErrorMessage(Reason reason) {
    super(MessageType.LOGIN_ERROR, serializer);
    this.reason = reason;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("reason", reason)
        .toString();
  }

  public static final Serializer<LoginErrorMessage> serializer =
      new Serializer<LoginErrorMessage>() {
    @Override
    public void serialize(LoginErrorMessage msg, ByteSink sink) {
      IntegerSerializer.singleton.serialize(msg.reason.ordinal(), sink);
    }

    @Override
    public LoginErrorMessage deserialize(ByteSource source) {
      int ordinal = IntegerSerializer.singleton.deserialize(source);
      return new LoginErrorMessage(Reason.values()[ordinal]);
    }
  };

  public static enum Reason {
    MISSING_USERNAME_OR_EMAIL,
    BAD_USERNAME_OR_EMAIL,
    MISSING_PASSWORD,
    BAD_PASSWORD
  }
}
