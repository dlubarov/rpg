package rpg.client;

import rpg.client.handlers.CharacterInfoHandler;
import rpg.client.handlers.ConfirmationHandler;
import rpg.client.handlers.LoginErrorHandler;
import rpg.client.handlers.NewCharacterErrorHandler;
import rpg.client.handlers.NewCharacterSuccessHandler;
import rpg.client.handlers.PeerDataHandler;
import rpg.client.handlers.RegistrationAcceptanceHandler;
import rpg.client.handlers.RegistrationErrorHandler;
import rpg.client.handlers.WelcomeHandler;
import rpg.net.ToServerMessageSink;
import rpg.net.UUIDCache;
import rpg.net.msg.ConfirmationMessage;
import rpg.net.msg.MessageType;
import rpg.net.msg.s2c.CharacterInfoMessage;
import rpg.net.msg.s2c.LoginErrorMessage;
import rpg.net.msg.s2c.NewCharacterErrorMessage;
import rpg.net.msg.s2c.NewCharacterSuccessMessage;
import rpg.net.msg.s2c.peer.PeerDataMessage;
import rpg.net.msg.s2c.peer.PeerGoodbye;
import rpg.net.msg.s2c.peer.PeerIntroduction;
import rpg.net.msg.s2c.peer.PeerUpdate;
import rpg.net.msg.s2c.RegistrationAcceptanceMessage;
import rpg.net.msg.s2c.RegistrationErrorMessage;
import rpg.net.msg.s2c.WelcomeMessage;
import rpg.util.Logger;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.LongSerializer;

/**
 * Dispatches messages send from the server to the client.
 */
public class MessageDispatcher implements Runnable {
  private final ByteSource source;

  public MessageDispatcher(ByteSource source) {
    this.source = source;
  }

  @Override public void run() {
    try {
      tryRun();
    } catch (Exception e) {
      Logger.error(e, "Error during message dispatch.");
    }
  }

  public void tryRun() {
    long uuid = LongSerializer.singleton.deserialize(source);
    byte messageTypeOrd = source.take();

    MessageType msgType = MessageType.fromOrdinal(messageTypeOrd);
    Logger.debug("Received message of type %s.", msgType);

    if (uuid != 0) {
      if (UUIDCache.recentlySawUUID(uuid)) {
        Logger.info("Recently saw message with UUID=%d; assuming it's a duplicate.", uuid);
        return;
      }
      UUIDCache.addUUID(uuid);
      ToServerMessageSink.singleton.sendWithoutConfirmation(new ConfirmationMessage(uuid));
    }

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
      case REGISTRATION_ACCEPTANCE:
        RegistrationAcceptanceHandler.singleton.handle(RegistrationAcceptanceMessage.serializer.deserialize(source));
        break;
      case REGISTRATION_ERROR:
        RegistrationErrorHandler.singleton.handle(RegistrationErrorMessage.serializer.deserialize(source));
        break;
      case LOGIN_ERROR:
        LoginErrorHandler.singleton.handle(LoginErrorMessage.serializer.deserialize(source));
        break;
      case NEW_CHARACTER_SUCCESS:
        NewCharacterSuccessHandler.singleton.handle(NewCharacterSuccessMessage.serializer.deserialize(source));
        break;
      case NEW_CHARACTER_ERROR:
        NewCharacterErrorHandler.singleton.handle(NewCharacterErrorMessage.serializer.deserialize(source));
        break;
      case CHARACTER_INFO:
        CharacterInfoHandler.singleton.handle(CharacterInfoMessage.serializer.deserialize(source));
        break;
      case WELCOME:
        WelcomeHandler.singleton.handle(WelcomeMessage.serializer.deserialize(source));
        break;
      case PEER_DATA:
        PeerDataHandler.singleton.handle(PeerDataMessage.serializer.deserialize(source));
        break;
      default:
        Logger.warning("Received a message of type %s, which is not handled.", msgType);
    }
  }
}
