package rpg.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import rpg.game.MotionState;
import rpg.util.ToStringBuilder;
import rpg.util.math.Vector3;
import rpg.util.phys.Positioned;

import static java.util.Collections.synchronizedMap;

public final class ActivePlayer implements Positioned {
  public final Session session;
  public final PlayerCharacter character;
  private final Map<ActivePlayer, MotionState> peerSnapshots;

  public ActivePlayer(Session session, PlayerCharacter character) {
    this.session = session;
    this.character = character;
    this.peerSnapshots = synchronizedMap(new HashMap<ActivePlayer, MotionState>());

    AccountManager.login(this);
  }

  public Collection<ActivePlayer> getSavedNeighbors() {
    return peerSnapshots.keySet();
  }

  public boolean inView(ActivePlayer peer) {
    return peerSnapshots.containsKey(peer);
  }

  public double errorInViewOf(ActivePlayer player) {
    MotionState view = peerSnapshots.get(player);
    return view.errorComparedTo(character.getMotionState());
  }

  public void logout() {
    AccountManager.logout(this);
  }

  @Override public Vector3 getPos() {
    return character.getMotionState().position;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("character", character)
        .append("peerSnapshots", peerSnapshots)
        .toString();
  }
}
