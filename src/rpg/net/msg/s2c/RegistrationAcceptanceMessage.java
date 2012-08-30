package rpg.net.msg.s2c;

import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.Serializer;

public class RegistrationAcceptanceMessage extends ServerToClientMessage {
  public RegistrationAcceptanceMessage() {
    super(MessageType.REGISTRATION_ACCEPTANCE, serializer);
  }

  @Override public String toString() {
    return new ToStringBuilder(this).toString();
  }

  public static final Serializer<RegistrationAcceptanceMessage> serializer =
      new Serializer<RegistrationAcceptanceMessage>() {
        @Override public void serialize(RegistrationAcceptanceMessage msg, ByteSink sink) {}

        @Override public RegistrationAcceptanceMessage deserialize(ByteSource source) {
          return new RegistrationAcceptanceMessage();
        }
  };
}
