package rpg.msg;

import rpg.serialization.ByteSink;
import rpg.serialization.IntegerSerializer;
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
    IntegerSerializer.singleton.serialize(type.ordinal(), sink);
    serializer.serialize(this, sink);
  }

  @Override
  public abstract String toString();
}
