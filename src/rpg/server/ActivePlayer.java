package rpg.server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import rpg.msg.Message;

import static java.util.Collections.synchronizedMap;

public final class ActivePlayer {
  private final PlayerCharacter character;
  private final InetAddress address;
  private final Map<PlayerCharacter, MotionSnapshot> peerSnapshots;

  public ActivePlayer(PlayerCharacter character, InetAddress address) {
    this.character = character;
    this.address = address;
    this.peerSnapshots = synchronizedMap(new HashMap<PlayerCharacter, MotionSnapshot>());
  }

  public void send(Message msg) {
    // FIXME: Send message to player.
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
