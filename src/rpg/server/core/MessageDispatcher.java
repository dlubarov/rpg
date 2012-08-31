package rpg.server.core;

import java.net.InetAddress;
import rpg.net.ToClientMessageSink;
import rpg.net.UUIDCache;
import rpg.net.msg.ConfirmationMessage;
import rpg.net.msg.MessageType;
import rpg.net.msg.c2s.CharacterSelectedMessage;
import rpg.net.msg.c2s.HereIAmMessage;
import rpg.net.msg.c2s.LoginMessage;
import rpg.net.msg.c2s.NewCharacterMessage;
import rpg.net.msg.c2s.RegistrationMessage;
import rpg.net.msg.c2s.SessionCreationMessage;
import rpg.server.active.Session;
import rpg.server.active.SessionManager;
import rpg.server.handlers.CharacterSelectedHandler;
import rpg.server.handlers.ConfirmationHandler;
import rpg.server.handlers.HereIAmHandler;
import rpg.server.handlers.LoginHandler;
import rpg.server.handlers.NewCharacterHandler;
import rpg.server.handlers.RegistrationHandler;
import rpg.util.Logger;
import rpg.util.NiftyException;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.LongSerializer;

/**
 * Dispatches a message sent from a client to the server.
 */
public class MessageDispatcher implements Runnable {
  private final ByteSource source;
  private final InetAddress clientAddr;

  public MessageDispatcher(ByteSource source, InetAddress clientAddr) {
    this.source = source;
    this.clientAddr = clientAddr;
  }

  @Override public void run() {
    try {
      tryRun();
    } catch (Exception e) {
      Logger.error(e, "Error during message dispatch.");
    }
  }

  public void tryRun() {
    // FIXME: Remove fake lag.
    try { Thread.sleep(50); } catch (InterruptedException e) {}

    long sessionID = LongSerializer.singleton.deserialize(source);
    long uuid = LongSerializer.singleton.deserialize(source);
    byte messageTypeOrd = source.take();

    MessageType msgType = MessageType.fromOrdinal(messageTypeOrd);
    Logger.debug("Received message of type %s.", msgType);

    Session clientSession;
    if (msgType == MessageType.SESSION_CREATION) {
      SessionCreationMessage msg = SessionCreationMessage.serializer.deserialize(source);
      clientSession = new Session(sessionID, clientAddr, msg.clientPort);
    } else {
      clientSession = SessionManager.getByID(sessionID);
      if (clientSession == null)
        throw new NiftyException("Bad session ID: %d.", sessionID);
    }

    if (uuid != 0) {
      if (UUIDCache.recentlySawUUID(uuid)) {
        Logger.info("Recently saw message with UUID=%d; assuming it's a duplicate.", uuid);
        return;
      }
      UUIDCache.addUUID(uuid);
      new ToClientMessageSink(clientSession).sendWithoutConfirmation(new ConfirmationMessage(uuid));
    }

    try {
      dispatch(msgType, clientSession);
    } catch (Exception e) {
      throw new NiftyException(e, "Exception in handling of %s.", msgType);
    }
  }

  private void dispatch(MessageType msgType, Session clientSession) throws Exception {
    switch (msgType) {
      case SESSION_CREATION:
        // Already handled specially.
        break;
      case CONFIRMATION:
        ConfirmationHandler.singleton.handle(ConfirmationMessage.serializer.deserialize(source), clientSession);
        break;
      case REGISTRATION_REQUEST:
        RegistrationHandler.singleton.handle(RegistrationMessage.serializer.deserialize(source), clientSession);
        break;
      case LOGIN_REQUEST:
        LoginHandler.singleton.handle(LoginMessage.serializer.deserialize(source), clientSession);
        break;
      case NEW_CHARACTER:
        NewCharacterHandler.singleton.handle(NewCharacterMessage.serializer.deserialize(source), clientSession);
        break;
      case CHARACTER_SELECTED:
        CharacterSelectedHandler.singleton.handle(CharacterSelectedMessage.serializer.deserialize(source), clientSession);
        break;
      case HERE_I_AM:
        HereIAmHandler.singleton.handle(HereIAmMessage.serializer.deserialize(source), clientSession);
        break;
      default:
        Logger.warning("Received a message of type %s, which is not handled.", msgType);
    }
  }
}
