package rpg.server;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

public final class ActivePlayer {
  private final PlayerCharacter character;
  private final Map<PlayerCharacter, MotionSnapshot> peerSnapshots;

  public ActivePlayer(PlayerCharacter character) {
    this.character = character;
    this.peerSnapshots = synchronizedMap(new HashMap<PlayerCharacter, MotionSnapshot>());
  }

  public boolean inView(PlayerCharacter peer) {
    return peerSnapshots.containsKey(peer);
  }

  public double errorInViewOf(PlayerCharacter character) {
    MotionSnapshot view = peerSnapshots.get(character);
    return errorFor(
        view.position.euclideanDistanceTo(character.getPos()),
        view.velocity.euclideanDistanceTo(character.getVel()),
        view.direction.euclideanDistanceTo(character.getDir()));
  }

  private static double errorFor(double posErr, double velErr, double dirErr) {
    return posErr + velErr + dirErr;
  }
}
