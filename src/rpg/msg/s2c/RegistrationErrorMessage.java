package rpg.msg.s2c;

import rpg.msg.Message;
import rpg.ser.ByteSink;
import rpg.ser.ByteSource;
import rpg.ser.IntegerSerializer;
import rpg.ser.Serializer;
import rpg.util.ToStringBuilder;

/**
 * A {@link Message} informing the client that their request for registration was declined.
 */
public class RegistrationErrorMessage extends Message {
  public final Reason reason;

  public RegistrationErrorMessage(Reason reason) {
    this.reason = reason;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("reason", reason)
        .toString();
  }

  public static final Serializer<RegistrationErrorMessage> serializer =
      new Serializer<RegistrationErrorMessage>() {
    @Override
    public void serialize(RegistrationErrorMessage msg, ByteSink sink) {
      IntegerSerializer.singleton.serialize(msg.reason.ordinal(), sink);
    }

    @Override
    public RegistrationErrorMessage deserialize(ByteSource source) {
      int ordinal = IntegerSerializer.singleton.deserialize(source);
      return new RegistrationErrorMessage(Reason.values()[ordinal]);
    }
  };

  public static enum Reason {
    USERNAME_TAKEN, USERNAME_BAD_CHARS,
    USERNAME_SHORT, USERNAME_LONG,
    PASSWORD_SHORT, PASSWORD_LONG
  }
}
