package rpg.client;

import rpg.client.handlers.ConfirmationHandler;
import rpg.core.Logger;
import rpg.msg.ConfirmationMessage;
import rpg.msg.MessageType;
import rpg.net.UuidCache;
import rpg.serialization.ByteSource;
import rpg.serialization.LongSerializer;

/**
 * Dispatches messages send from the server to the client.
 */
public class MessageDispatcher implements Runnable {
  private final ByteSource source;

  public MessageDispatcher(ByteSource source) {
    this.source = source;
  }

  @Override
  public void run() {
    long uuid = LongSerializer.singleton.deserialize(source);
    if (uuid != 0) {
      UuidCache.addUuid(uuid);
      // FIXME: Send confirmation message.
    }
    byte msgId = source.take();
    MessageType msgType;
    try {
      msgType = MessageType.values()[msgId];
    } catch (ArrayIndexOutOfBoundsException e) {
      Logger.warning("Bad message ID: %d.", msgId);
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
        ConfirmationHandler.singleton.handle(msg);
        break;
      }
      case REGISTRATION_ERROR: {
        // FIXME
        break;
      }
      case LOGIN_ERROR: {
        // FIXME
        break;
      }
      case WELCOME: {
        // FIXME
        break;
      }
      case PEER_INTRODUCTION: {
        // FIXME
        break;
      }
      case PEER_UPDATE: {
        // FIXME
        break;
      }
      case PEER_GOODBYE: {
        // FIXME
        break;
      }
      default: {
        Logger.warning("Received a message of type %s, which is not handled.", msgType);
      }
    }
  }
}
