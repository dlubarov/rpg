package rpg.msg;

public enum MessageType {
  CONFIRMATION,

  // Client to server.
  REGISTRATION_REQUEST,
  LOGIN_REQUEST,
  HERE_I_AM,

  // Server to client.
  REGISTRATION_ERROR,
  LOGIN_ERROR,
  CHARACTER_INFO,
  WELCOME,
  PEER_INTRODUCTION,
  PEER_UPDATE,
  PEER_GOODBYE,
}
