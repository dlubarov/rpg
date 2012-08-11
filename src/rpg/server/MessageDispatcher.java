package rpg.server;

import java.net.InetAddress;
import rpg.msg.ConfirmationMessage;
import rpg.msg.MessageType;
import rpg.msg.c2s.HereIAmMessage;
import rpg.msg.c2s.LoginMessage;
import rpg.msg.c2s.NewCharacterMessage;
import rpg.msg.c2s.RegistrationMessage;
import rpg.net.ToClientMessageSink;
import rpg.net.UUIDCache;
import rpg.serialization.ByteSource;
import rpg.serialization.LongSerializer;
import rpg.server.handlers.ConfirmationHandler;
import rpg.server.handlers.HereIAmHandler;
import rpg.server.handlers.LoginHandler;
import rpg.server.handlers.NewCharacterHandler;
import rpg.server.handlers.RegistrationHandler;
import rpg.util.Logger;

/**
 * Dispatches a message sent from a client to the server.
 */
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
      UUIDCache.addUUID(uuid);
      new ToClientMessageSink(sender).sendWithoutConfirmation(new ConfirmationMessage(uuid));
    }
    byte msgId = source.take();
    MessageType msgType;
    try {
      msgType = MessageType.values()[msgId];
    } catch (ArrayIndexOutOfBoundsException e) {
      Logger.warning("Bad message ID: %d.", msgId);
      return;
    }

    Logger.debug("Received message of type %s.", msgType);
    try {
      dispatch(msgType);
    } catch (Exception e) {
      Logger.error(e, "Exception in handling of %s.", msgType);
    }
  }

  private void dispatch(MessageType msgType) throws Exception {
    switch (msgType) {
      case CONFIRMATION:
        ConfirmationHandler.singleton.handle(ConfirmationMessage.serializer.deserialize(source));
        break;
      case REGISTRATION_REQUEST:
        RegistrationHandler.singleton.handle(RegistrationMessage.serializer.deserialize(source), sender);
        break;
      case LOGIN_REQUEST:
        LoginHandler.singleton.handle(LoginMessage.serializer.deserialize(source), sender);
        break;
      case NEW_CHARACTER:
        NewCharacterHandler.singleton.handle(NewCharacterMessage.serializer.deserialize(source), sender);
        break;
      case HERE_I_AM:
        HereIAmHandler.singleton.handle(HereIAmMessage.serializer.deserialize(source), sender);
        break;
      default:
        Logger.warning("Received a message of type %s, which is not handled.", msgType);
    }
  }
}
