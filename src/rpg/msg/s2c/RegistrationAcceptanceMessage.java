package rpg.msg.s2c;

import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

public class RegistrationAcceptanceMessage extends Message {
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
