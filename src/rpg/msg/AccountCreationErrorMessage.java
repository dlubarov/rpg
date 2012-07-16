package rpg.msg;

import rpg.ser.ByteSink;
import rpg.ser.ByteSource;
import rpg.ser.IntegerSerializer;
import rpg.ser.Serializer;
import rpg.util.ToStringBuilder;

public class AccountCreationErrorMessage extends Message {
  public final Reason reason;

  public AccountCreationErrorMessage(Reason reason) {
    this.reason = reason;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("reason", reason)
        .toString();
  }

  public static final Serializer<AccountCreationErrorMessage> serializer =
      new Serializer<AccountCreationErrorMessage>() {
    @Override
    public void serialize(AccountCreationErrorMessage msg, ByteSink sink) {
      IntegerSerializer.singleton.serialize(msg.reason.ordinal(), sink);
    }

    @Override
    public AccountCreationErrorMessage deserialize(ByteSource source) {
      int ordinal = IntegerSerializer.singleton.deserialize(source);
      return new AccountCreationErrorMessage(Reason.values()[ordinal]);
    }
  };

  public static enum Reason {
    USERNAME_TAKEN, USERNAME_BAD_CHARS,
    USERNAME_SHORT, USERNAME_LONG,
    PASSWORD_SHORT, PASSWORD_LONG
  }
}
