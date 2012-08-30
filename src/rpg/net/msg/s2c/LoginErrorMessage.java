package rpg.net.msg.s2c;

import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;

/**
 * A {@link rpg.net.msg.Message} informing the client that their login was declined.
 */
public class LoginErrorMessage extends ServerToClientMessage {
  public final Reason reason;

  public LoginErrorMessage(Reason reason) {
    super(MessageType.LOGIN_ERROR, serializer);
    this.reason = reason;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("reason", reason)
        .toString();
  }

  public static final Serializer<LoginErrorMessage> serializer =
      new Serializer<LoginErrorMessage>() {
        @Override public void serialize(LoginErrorMessage msg, ByteSink sink) {
          IntegerSerializer.singleton.serialize(msg.reason.ordinal(), sink);
        }

        @Override public LoginErrorMessage deserialize(ByteSource source) {
          int ordinal = IntegerSerializer.singleton.deserialize(source);
          return new LoginErrorMessage(Reason.values()[ordinal]);
        }
      };

  public static enum Reason {
    MISSING_EMAIL("You must enter an email."),
    BAD_EMAIL("No user with that email was found."),
    MISSING_PASSWORD("You must enter a password."),
    BAD_PASSWORD("The password you entered did not match our records."),
    BAD_VERSION("Your client is out of date. Please download the latest version.");

    public final String message;

    private Reason(String message) {
      this.message = message;
    }
  }
}
