package rpg.server;

import java.net.InetAddress;
import rpg.core.Logger;
import rpg.msg.ConfirmationMessage;
import rpg.msg.MessageType;
import rpg.msg.c2s.HereIAmMessage;
import rpg.msg.c2s.RegistrationRequestMessage;
import rpg.serialization.ByteSource;
import rpg.serialization.LongSerializer;
import rpg.server.handlers.HereIAmHandler;
import rpg.server.handlers.RegistrationRequestHandler;

public class MessageDispatcher implements Runnable {
  private final ByteSource source;
  private final InetAddress sender;

  public MessageDispatcher(ByteSource source, InetAddress sender) {
    this.source = source;
    this.sender = sender;
  }

  @Override
  public void run() {
    long uuid = LongSerializer.singleton.deserialize(source);
    if (uuid != 0) {
      ;
    }
    byte msgId = source.take();
    MessageType msgType;
    try {
      msgType = MessageType.values()[msgId];
    } catch (ArrayIndexOutOfBoundsException e) {
      Logger.error(e, "Bad message ID.");
      return;
    }

    try {
      dispatch(msgType);
    } catch (Exception e) {
      Logger.error(e, "Exception in handling of %s.", msgType);
    }
  }

  private void dispatch(MessageType msgType) throws Exception {
    switch (msgType) {
      case CONFIRMATION: {
        ConfirmationMessage msg = ConfirmationMessage.serializer.deserialize(source);
        // FIXME handle
        break;
      }
      case HERE_I_AM: {
        HereIAmMessage msg = HereIAmMessage.serializer.deserialize(source);
        HereIAmHandler.singleton.handle(msg, sender);
        break;
      }
      case REGISTRATION_REQUEST: {
        RegistrationRequestMessage msg = RegistrationRequestMessage.serializer.deserialize(source);
        RegistrationRequestHandler.singleton.handle(msg, sender);
        break;
      }
    }
  }
}
