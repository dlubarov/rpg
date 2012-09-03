package rpg.server.active;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import rpg.net.ToClientMessageSink;
import rpg.net.msg.s2c.PeerGoodbyeMessage;
import rpg.net.msg.s2c.PeerIntroductionMessage;
import rpg.net.msg.s2c.PeerUpdateMessage;
import rpg.server.account.PlayerCharacter;
import rpg.util.Logger;

public class UpdatePlayerTask implements Runnable {
  // Updates per second.
  private static final double PERIOD = 0.5;

  private static final ScheduledExecutorService executor =
      Executors.newScheduledThreadPool(4, new ThreadFactory() {
        @Override public Thread newThread(Runnable runnable) {
          return new Thread(runnable, UpdatePlayerTask.class.getSimpleName());
        }
      });

  // A peer inside the inner radius must be considered a neighbor.
  // A peer outside the outer radius must not be considered a neighbor.
  private static final double INNER_RADIUS = 60, OUTER_RADIUS = 100;

  public final ActivePlayer player;

  public UpdatePlayerTask(ActivePlayer player) {
    this.player = player;
    executor.scheduleAtFixedRate(this, 0, (int) (PERIOD * 1000), TimeUnit.MILLISECONDS);
  }

  @Override public void run() {
    try {
      tryRun();
    } catch (Exception e) {
      Logger.error(e, "Exception in update player task.");
    } catch (Throwable t) {
      Logger.fatal(t, "Fatal error in update player task.");
    }
  }

  private void tryRun() throws Exception {
    RealmAdmin realmAdmin = RealmAdmin.getAdminFor(player.character.getRealm());
    Collection<ActivePlayer> savedNeighbors = player.getSavedNeighbors();
    Collection<ActivePlayer> closeNeighbors = realmAdmin.getNeighbors(player, INNER_RADIUS);
    Collection<ActivePlayer> farNeighbors = realmAdmin.getNeighbors(player, OUTER_RADIUS);

    Collection<ActivePlayer> newNeighbors = new HashSet<ActivePlayer>(closeNeighbors);
    newNeighbors.removeAll(savedNeighbors);

    Collection<ActivePlayer> deadNeighbors = new HashSet<ActivePlayer>(savedNeighbors);
    deadNeighbors.removeAll(farNeighbors);

    sendIntroductions(newNeighbors);
    sendGoodbyes(deadNeighbors);
    sendUpdates();
  }

  private void sendIntroductions(Collection<ActivePlayer> newNeighbors) {
    if (newNeighbors.isEmpty()) {
      return;
    }

    List<PeerIntroductionMessage.Part> parts =
        new ArrayList<PeerIntroductionMessage.Part>(newNeighbors.size());
    for (ActivePlayer peer : newNeighbors) {
      player.addNeighbor(peer);
      PlayerCharacter character = peer.character;
      PeerIntroductionMessage.Part introduction = new PeerIntroductionMessage.Part(
          character.id, character.name, character.combatClass, character.getMotionState());
      parts.add(introduction);
    }

    PeerIntroductionMessage msg = new PeerIntroductionMessage(parts);
    new ToClientMessageSink(player.session).sendWithConfirmation(msg, 3);
  }

  private void sendGoodbyes(Collection<ActivePlayer> deadNeighbors) {
    if (deadNeighbors.isEmpty()) {
      return;
    }

    List<Integer> ids = new ArrayList<Integer>(deadNeighbors.size());
    for (ActivePlayer peer : deadNeighbors) {
      player.removeNeighbor(peer);
      ids.add(peer.character.id);
    }

    PeerGoodbyeMessage msg = new PeerGoodbyeMessage(ids);
    new ToClientMessageSink(player.session).sendWithConfirmation(msg, 2);
  }

  private void sendUpdates() {
    // FIXME: This is very naive and not scalable.
    // Should sort peers by snapshot error magnitudes.

    List<PeerUpdateMessage.Part> parts = new ArrayList<PeerUpdateMessage.Part>();
    for (ActivePlayer peer : player.getSavedNeighbors()) {
      PeerUpdateMessage.Part part = new PeerUpdateMessage.Part(
          peer.character.id, peer.character.getMotionState());
      parts.add(part);
    }

    if (!parts.isEmpty()) {
      PeerUpdateMessage msg = new PeerUpdateMessage(parts);
      new ToClientMessageSink(player.session).sendWithoutConfirmation(msg);
    }
  }
}
