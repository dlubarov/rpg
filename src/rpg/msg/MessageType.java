package rpg.msg;

public enum MessageType {
  CONFIRMATION,

  // Client to server.
  REGISTRATION_REQUEST,
  HERE_I_AM,

  // Server to client.
  REGISTRATION_ERROR,
  WELCOME,
  PEER_INTRODUCTION,
  PEER_GOODBYE,
  PEER_UPDATE
}
