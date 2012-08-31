package rpg.net;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import rpg.net.msg.Message;
import rpg.util.Logger;

public class RetryQueue {
  private static final ScheduledExecutorService scheduler =
      Executors.newScheduledThreadPool(1, new ThreadFactory() {
        @Override public Thread newThread(Runnable runnable) {
          return new Thread(runnable, "Message Retryer");
        }
      });

  private static final Set<Long> activeMessageUUIDs =
      Collections.synchronizedSet(new HashSet<Long>());

  public static void startRetrying(
      MessageSink sink, Message msg,
      int retries, long delayMillis,
      Runnable onConfirmation, Runnable onTimeout) {
    RetryJob retryJob = new RetryJob(sink, msg, delayMillis, retries,
        onConfirmation, onTimeout);
    scheduler.schedule(retryJob, 0, TimeUnit.MILLISECONDS);
  }

  public static void stopRetrying(long uuid) {
    Logger.debug("No longer retrying %d", uuid);
    activeMessageUUIDs.remove(uuid);
  }

  private static class RetryJob implements Runnable {
    private final MessageSink sink;
    private final Message msg;
    private final long uuid;
    private final long delayMillis;
    private final Runnable onConfirmation, onTimeout;
    private int retriesLeft;

    private RetryJob(MessageSink sink, Message msg, long delayMillis, int retries,
        Runnable onConfirmation, Runnable onTimeout) {
      this.sink = sink;
      this.msg = msg;
      this.uuid = UUIDGenerator.generate();
      this.delayMillis = delayMillis;
      this.retriesLeft = retries;
      this.onConfirmation = onConfirmation;
      this.onTimeout = onTimeout;
      activeMessageUUIDs.add(uuid);
    }

    @Override public void run() {
      if (activeMessageUUIDs.contains(uuid)) {
        sink.sendOnce(msg, uuid);
        if (--retriesLeft > 0)
          scheduleAnotherRun();
        else {
          runCallback(onTimeout);
          giveUp();
        }
      } else {
        runCallback(onConfirmation);
      }
    }

    private void scheduleAnotherRun() {
      scheduler.schedule(this, delayMillis, TimeUnit.MILLISECONDS);
    }

    private void giveUp() {
      activeMessageUUIDs.remove(uuid);
    }

    private void runCallback(Runnable callback) {
      if (callback != null)
        try {
          callback.run();
        } catch (Exception e) {
          Logger.error(e, "Exception during callback.");
        }
    }
  }
}
