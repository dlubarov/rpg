package rpg.net.msg.c2s;

import rpg.game.MotionState;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.Serializer;

public class LogoutMessage extends ClientToServerMessage {
  protected LogoutMessage() {
    super(MessageType.LOGOUT_REQUEST, serializer);
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .toString();
  }

  public static final Serializer<LogoutMessage> serializer = new Serializer<LogoutMessage>() {
    @Override public void serialize(LogoutMessage msg, ByteSink sink) {}

    @Override public LogoutMessage deserialize(ByteSource source) {
      return new LogoutMessage();
    }
  };
}
