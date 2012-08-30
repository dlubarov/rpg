package rpg.net.msg.c2s;

import rpg.net.msg.Message;
import rpg.net.msg.MessageType;
import rpg.util.serialization.Serializer;

public abstract class ClientToServerMessage extends Message {
  protected ClientToServerMessage(MessageType type, Serializer serializer) {
    super(type, serializer);
  }
}
