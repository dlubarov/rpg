package rpg.net.msg.c2s;

import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;

public class SessionCreationMessage extends ClientToServerMessage {
  public final int clientPort;

  public SessionCreationMessage(int clientPort) {
    super(MessageType.SESSION_CREATION, serializer);
    this.clientPort = clientPort;
  }

  public static final Serializer<SessionCreationMessage> serializer =
      new Serializer<SessionCreationMessage>() {
        @Override public void serialize(SessionCreationMessage msg, ByteSink sink) {
          IntegerSerializer.singleton.serialize(msg.clientPort, sink);
        }

        @Override public SessionCreationMessage deserialize(ByteSource source) {
          return new SessionCreationMessage(IntegerSerializer.singleton.deserialize(source));
        }
      };

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("clientPort", clientPort)
        .toString();
  }
}
