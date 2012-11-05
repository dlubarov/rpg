package rpg.server.active;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import rpg.game.MotionState;
import rpg.server.account.AccountManager;
import rpg.server.account.PlayerCharacter;
import rpg.util.Timing;
import rpg.util.ToStringBuilder;
import rpg.util.math.Vector3;
import rpg.util.phys.Positioned;

import static java.util.Collections.synchronizedMap;

public final class ActivePlayer implements Positioned {
  public final Session session;
  public final PlayerCharacter character;
  private final Map<ActivePlayer, MotionState> peerSnapshots;
  private double lastUpdatedAt = Timing.currentTime();

  public ActivePlayer(Session session, PlayerCharacter character) {
    this.session = session;
    this.character = character;
    this.peerSnapshots = synchronizedMap(new HashMap<ActivePlayer, MotionState>());

    AccountManager.login(this);
    getRealmAdmin().playerEntering(this);
  }

  public void moveTo(MotionState motionState) {
    getRealmAdmin().playerExiting(this);
    character.setMotionSnapshot(motionState);
    getRealmAdmin().playerEntering(this);
  }

  public double getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public void justUpdated() {
    lastUpdatedAt = Timing.currentTime();
  }

  public Collection<ActivePlayer> getSavedNeighbors() {
    return peerSnapshots.keySet();
  }

  public void addNeighbor(ActivePlayer peer) {
    peerSnapshots.put(peer, peer.character.getExtrapolatedMotionState());
  }

  public void removeNeighbor(ActivePlayer peer) {
    peerSnapshots.remove(peer);
  }

  public boolean inView(ActivePlayer peer) {
    return peerSnapshots.containsKey(peer);
  }

  public double errorInViewOf(ActivePlayer player) {
    MotionState view = peerSnapshots.get(player);
    return view.errorComparedTo(character.getExtrapolatedMotionState());
  }

  // Logs out this player. This is not the same as terminating the session, as the user
  // could select a different character and continue playing.
  public void logout() {
    AccountManager.logout(this);
    getRealmAdmin().playerExiting(this);
  }

  // FIXME: This is always changing, will be a problem for octrees.
  @Override public Vector3 getPos() {
    return character.getMotionSnapshot().position;
  }

  private RealmAdmin getRealmAdmin() {
    return RealmAdmin.getAdminFor(character.getRealm());
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("character", character)
        .append("lastUpdatedAt", lastUpdatedAt)
        .toString();
  }
}
