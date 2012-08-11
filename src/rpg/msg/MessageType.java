package rpg.msg;

public enum MessageType {
  CONFIRMATION,

  // Client to server.
  REGISTRATION_REQUEST,
  LOGIN_REQUEST,
  NEW_CHARACTER,
  HERE_I_AM,

  // Server to client.
  REGISTRATION_ACCEPTANCE,
  REGISTRATION_ERROR,
  LOGIN_ERROR,
  NEW_CHARACTER_SUCCESS,
  CHARACTER_INFO,
  WELCOME,
  PEER_INTRODUCTION,
  PEER_UPDATE,
  PEER_GOODBYE,
}
