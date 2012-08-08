package rpg.msg;

import rpg.serialization.ByteSink;
import rpg.serialization.Serializer;

public abstract class Message {
  public final MessageType type;
  private final Serializer serializer;

  protected Message(MessageType type, Serializer serializer) {
    this.type = type;
    this.serializer = serializer;
  }

  @SuppressWarnings("unchecked")
  public void serializeWithTypeTo(ByteSink sink) {
    sink.give((byte) type.ordinal());
    serializer.serialize(this, sink);
  }

  @Override
  public abstract String toString();
}
