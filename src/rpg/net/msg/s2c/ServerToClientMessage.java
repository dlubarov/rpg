package rpg.net.msg.s2c;

import rpg.net.msg.Message;
import rpg.net.msg.MessageType;
import rpg.util.serialization.Serializer;

public abstract class ServerToClientMessage extends Message {
  protected ServerToClientMessage(MessageType type, Serializer serializer) {
    super(type, serializer);
  }
}
