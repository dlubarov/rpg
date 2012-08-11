package rpg.client;

import rpg.client.handlers.CharacterInfoHandler;
import rpg.client.handlers.ConfirmationHandler;
import rpg.client.handlers.LoginErrorHandler;
import rpg.client.handlers.NewCharacterSuccessHandler;
import rpg.client.handlers.PeerGoodbyeHandler;
import rpg.client.handlers.PeerIntroductionHandler;
import rpg.client.handlers.PeerUpdateHandler;
import rpg.client.handlers.RegistrationAcceptanceHandler;
import rpg.client.handlers.RegistrationErrorHandler;
import rpg.client.handlers.WelcomeHandler;
import rpg.msg.ConfirmationMessage;
import rpg.msg.MessageType;
import rpg.msg.s2c.CharacterInfoMessage;
import rpg.msg.s2c.LoginErrorMessage;
import rpg.msg.s2c.NewCharacterSuccessMessage;
import rpg.msg.s2c.PeerGoodbyeMessage;
import rpg.msg.s2c.PeerIntroductionMessage;
import rpg.msg.s2c.PeerUpdateMessage;
import rpg.msg.s2c.RegistrationAcceptanceMessage;
import rpg.msg.s2c.RegistrationErrorMessage;
import rpg.msg.s2c.WelcomeMessage;
import rpg.net.ToServerMessageSink;
import rpg.net.UUIDCache;
import rpg.serialization.ByteSource;
import rpg.serialization.LongSerializer;
import rpg.util.Logger;

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
      UUIDCache.addUUID(uuid);
      ToServerMessageSink.singleton.sendWithoutConfirmation(new ConfirmationMessage(uuid));
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
      case CHARACTER_INFO:
        CharacterInfoHandler.singleton.handle(CharacterInfoMessage.serializer.deserialize(source));
        break;
      case WELCOME:
        WelcomeHandler.singleton.handle(WelcomeMessage.serializer.deserialize(source));
        break;
      case PEER_INTRODUCTION:
        PeerIntroductionHandler.singleton.handle(PeerIntroductionMessage.serializer.deserialize(source));
        break;
      case PEER_UPDATE:
        PeerUpdateHandler.singleton.handle(PeerUpdateMessage.serializer.deserialize(source));
        break;
      case PEER_GOODBYE:
        PeerGoodbyeHandler.singleton.handle(PeerGoodbyeMessage.serializer.deserialize(source));
        break;
      default:
        Logger.warning("Received a message of type %s, which is not handled.", msgType);
    }
  }
}
