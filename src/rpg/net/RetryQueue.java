package rpg.net;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rpg.net.msg.Message;
import rpg.util.Logger;

public class RetryQueue {
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  private static final Set<Long> activeMessageUUIDs =
      Collections.synchronizedSet(new HashSet<Long>());

  public static void startRetrying(MessageSink sink, Message msg, int retries, long delayMillis) {
    RetryJob retryJob = new RetryJob(sink, msg, delayMillis, retries);
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
    private int retriesLeft;

    private RetryJob(MessageSink sink, Message msg, long delayMillis, int retries) {
      this.sink = sink;
      this.msg = msg;
      this.uuid = UUIDGenerator.generate();
      this.delayMillis = delayMillis;
      this.retriesLeft = retries;
      activeMessageUUIDs.add(uuid);
    }

    @Override public void run() {
      if (activeMessageUUIDs.contains(uuid)) {
        sink.sendOnce(msg, uuid);
        if (--retriesLeft > 0) {
          scheduleAnotherRun();
        } else {
          giveUp();
        }
      }
    }

    private void scheduleAnotherRun() {
      scheduler.schedule(this, delayMillis, TimeUnit.MILLISECONDS);
    }

    private void giveUp() {
      activeMessageUUIDs.remove(uuid);
    }
  }
}
