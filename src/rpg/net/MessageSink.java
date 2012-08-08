package rpg.net;

import java.util.Arrays;
import rpg.msg.Message;
import rpg.serialization.ByteSink;
import rpg.serialization.LongSerializer;
import rpg.util.Logger;

public abstract class MessageSink {
  // TODO: Be smart about this; use empirical latency.
  private static final int RETRY_AFTER_MILLIS = 500;

  protected abstract void sendRaw(byte[] data);

  @SuppressWarnings("unchecked")
  void sendOnce(Message msg, long uuid) {
    Logger.debug("Sending message: %s (uuid=%d)", msg, uuid);
    ByteSink sink = new ByteSink();
    LongSerializer.singleton.serialize(uuid, sink);
    msg.serializeWithTypeTo(sink);
    // TODO: Could use thread-local arrays rather than allocating new ones with getData.
    // This would involve adding offset & length parameters to sendRaw.
    byte[] data = sink.getData();
    if (data.length > 512)
      Logger.warning("Unsafe length %d of message %s.", data.length, msg);
    Logger.debug("Sending data: %s.", Arrays.toString(data));
    sendRaw(data);
  }

  public void sendWithoutConfirmation(Message msg) {
    sendOnce(msg, 0);
  }

  public void sendWithConfirmation(Message msg, int retries) {
    RetryQueue.startRetrying(this, msg, UUIDGenerator.generate(), retries, RETRY_AFTER_MILLIS);
  }
}
