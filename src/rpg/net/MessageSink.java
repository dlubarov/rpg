package rpg.net;

import rpg.core.Logger;
import rpg.msg.Message;
import rpg.serialization.ByteSink;
import rpg.serialization.IntegerSerializer;

public abstract class MessageSink {
  // TODO: Be smart about this; use empirical latency.
  private static final int RETRY_AFTER_MILLIS = 500;

  protected abstract void sendRaw(byte[] data);

  @SuppressWarnings("unchecked")
  public void sendOnce(Message msg, int uuid) {
    ByteSink sink = new ByteSink();
    IntegerSerializer.singleton.serialize(uuid, sink);
    msg.serializeWithTypeTo(sink);
    byte[] data = sink.getData();
    if (data.length > 512)
      Logger.warning("Unsafe length %d of message %s.", data.length, msg);
    sendRaw(data);
  }

  public void sendWithoutConfirmation(Message msg) {
    sendOnce(msg, 0);
  }

  public void sendWithConfirmation(Message msg, int retries) {
    RetryQueue.startRetrying(msg, UuidGenerator.generate(), retries, RETRY_AFTER_MILLIS);
  }
}
