package rpg.net.msg;

public enum MessageType {
  CONFIRMATION,

  // Client to server.
  SESSION_CREATION,
  REGISTRATION_REQUEST,
  LOGIN_REQUEST,
  NEW_CHARACTER,
  CHARACTER_SELECTED,
  HERE_I_AM,

  // Server to client.
  REGISTRATION_ACCEPTANCE,
  REGISTRATION_ERROR,
  LOGIN_ERROR,
  NEW_CHARACTER_SUCCESS,
  NEW_CHARACTER_ERROR,
  CHARACTER_INFO,
  WELCOME,
  PEER_INTRODUCTION,
  PEER_UPDATE,
  PEER_GOODBYE,
  ;

  public static MessageType fromOrdinal(int messageTypeOrd) {
    try {
      return MessageType.values()[messageTypeOrd];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Bad message ID: " + messageTypeOrd);
    }
  }
}
