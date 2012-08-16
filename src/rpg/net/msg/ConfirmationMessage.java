package rpg.net.msg;

import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.LongSerializer;
import rpg.util.serialization.Serializer;

public class ConfirmationMessage extends Message {
  public final Long uuid;

  public ConfirmationMessage(Long uuid) {
    super(MessageType.CONFIRMATION, serializer);
    this.uuid = uuid;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("uuid", uuid)
        .toString();
  }

  public static final Serializer<ConfirmationMessage> serializer =
      new Serializer<ConfirmationMessage>() {
        @Override public void serialize(ConfirmationMessage msg, ByteSink sink) {
          LongSerializer.singleton.serialize(msg.uuid, sink);
        }

        @Override public ConfirmationMessage deserialize(ByteSource source) {
          return new ConfirmationMessage(LongSerializer.singleton.deserialize(source));
        }
      };
}
