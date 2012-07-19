package rpg.net;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rpg.msg.Message;

public class RetryQueue {
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  private static final Set<Long> activeMessageUuids =
      Collections.synchronizedSet(new HashSet<Long>());

  public static void startRetrying(Message msg, long uuid, int retries, long delayMillis) {
    activeMessageUuids.add(uuid);
    Retrier retrier = new Retrier(msg, uuid, delayMillis, retries);
    scheduler.schedule(retrier, 0, TimeUnit.MILLISECONDS);
  }

  public static void stopRetrying(long uuid) {
    activeMessageUuids.remove(uuid);
  }

  private static class Retrier implements Runnable {
    private final Message msg;
    private final long uuid;
    private final long delayMillis;
    private int retriesLeft;

    private Retrier(Message msg, long uuid, long delayMillis, int retries) {
      this.msg = msg;
      this.uuid = uuid;
      this.delayMillis = delayMillis;
      this.retriesLeft = retries;
    }

    @Override
    public void run() {
      if (activeMessageUuids.contains(uuid)) {
        sendOnce();
        if (--retriesLeft > 0)
          scheduleAnotherRun();
        else
          giveUp();
      }
    }

    private void scheduleAnotherRun() {
      scheduler.schedule(this, delayMillis, TimeUnit.MILLISECONDS);
    }

    private void giveUp() {
      activeMessageUuids.remove(uuid);
    }

    private void sendOnce() {
      // FIXME send once
    }
  }
}
