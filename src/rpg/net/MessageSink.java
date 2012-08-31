package rpg.net;

import java.util.Arrays;
import rpg.net.msg.Message;
import rpg.util.Logger;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.LongSerializer;

public abstract class MessageSink {
  // TODO: Be smart about this; use empirical latency.
  private static final int RETRY_AFTER_MILLIS = 500;

  protected abstract void sendRaw(byte[] data);

  protected Long getSessionID() {
    return null;
  }

  @SuppressWarnings("unchecked")
  void sendOnce(Message msg, long uuid) {
    Logger.debug("Sending message: %s (uuid=%d)", msg, uuid);
    ByteSink sink = new ByteSink();
    if (getSessionID() != null)
      LongSerializer.singleton.serialize(getSessionID(), sink);
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

  public void sendWithConfirmation(Message msg, int retries,
      Runnable onConfirmation, Runnable onTimeout) {
    RetryQueue.startRetrying(this, msg, retries, RETRY_AFTER_MILLIS,
        onConfirmation, onTimeout);
  }

  public void sendWithConfirmation(Message msg, int retries) {
    sendWithConfirmation(msg, retries, null, null);
  }
}
