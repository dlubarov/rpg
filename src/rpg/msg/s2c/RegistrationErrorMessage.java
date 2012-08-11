package rpg.msg.s2c;

import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.net.NetConfig;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

/**
 * A {@link Message} informing the client that their request for registration was declined.
 */
public class RegistrationErrorMessage extends Message {
  public final Reason reason;

  public RegistrationErrorMessage(Reason reason) {
    super(MessageType.REGISTRATION_ERROR, serializer);
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
    EMAIL_MISSING("You must enter an email."),
    EMAIL_TAKEN("That email is already in use. Please use a different one."),
    EMAIL_BAD_FORMAT("The email you entered appears to be malformed."),
    EMAIL_LONG("Emails can be at most " + NetConfig.EMAIL_MAX_LEN + " characters long."),
    PASSWORD_MISSING("You must enter a password."),
    PASSWORD_SHORT("Passwords must be at least " + NetConfig.PASSWORD_MIN_LEN + " characters long."),
    PASSWORD_LONG("Password can be at most " + NetConfig.PASSWORD_MAX_LEN + " characters long."),
    PASSWORD_EASY("The password you entered is too easy to guess."),
    CONFIRMATION_MISSING("You must enter a password confirmation."),
    BAD_CONFIRMATION("The password confirmation did not match the original password."),
    BAD_VERSION("Your client is out of date. Please download the latest version.");

    public final String message;

    private Reason(String message) {
      this.message = message;
    }
  }
}
