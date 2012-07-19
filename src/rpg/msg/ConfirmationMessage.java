package rpg.msg;

import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.LongSerializer;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

public class ConfirmationMessage extends Message {
  public final Long uuid;

  public ConfirmationMessage(Long uuid) {
    super(MessageType.CONFIRMATION, serializer);
    this.uuid = uuid;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("uuid", uuid)
        .toString();
  }

  public static final Serializer<ConfirmationMessage> serializer =
      new Serializer<ConfirmationMessage>() {
    @Override
    public void serialize(ConfirmationMessage msg, ByteSink sink) {
      LongSerializer.singleton.serialize(msg.uuid, sink);
    }

    @Override
    public ConfirmationMessage deserialize(ByteSource source) {
      return new ConfirmationMessage(LongSerializer.singleton.deserialize(source));
    }
  };
}
