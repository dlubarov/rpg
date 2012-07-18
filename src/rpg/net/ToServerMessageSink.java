package rpg.net;

public class ToServerMessageSink extends MessageSink {
  public static final ToServerMessageSink singleton = new ToServerMessageSink();

  private ToServerMessageSink() {}

  @Override
  public void sendRaw(byte[] data) {
    // FIXME send data to server
  }
}
