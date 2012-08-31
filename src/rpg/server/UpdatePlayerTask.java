package rpg.server;

import java.util.Collection;
import java.util.HashSet;

public class UpdatePlayerTask implements Runnable {
  // A peer inside the inner radius must be considered a neighbor.
  // A peer outside the outer radius must not be considered a neighbor.
  private static final double INNER_RADIUS = 60, OUTER_RADIUS = 100;

  public final ActivePlayer player;

  public UpdatePlayerTask(ActivePlayer player) {
    this.player = player;
  }

  @Override public void run() {
    RealmAdmin realmAdmin = RealmAdmin.getAdminFor(player.character.getRealm());
    Collection<ActivePlayer> savedNeighbors = player.getSavedNeighbors();
    Collection<ActivePlayer> closeNeighbors = realmAdmin.getNeighbors(player, INNER_RADIUS);
    Collection<ActivePlayer> farNeighbors = realmAdmin.getNeighbors(player, OUTER_RADIUS);

    Collection<ActivePlayer> newNeighbors = new HashSet<ActivePlayer>(closeNeighbors);
    newNeighbors.removeAll(savedNeighbors);

    Collection<ActivePlayer> deadNeighbors = new HashSet<ActivePlayer>(savedNeighbors);
    deadNeighbors.removeAll(farNeighbors);

    // FIXME: Resubmit this task.
  }
}
