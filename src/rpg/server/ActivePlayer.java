package rpg.server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import rpg.net.ToClientMessageSink;

import static java.util.Collections.synchronizedMap;

public final class ActivePlayer {
  private final PlayerCharacter character;
  private final Map<PlayerCharacter, MotionSnapshot> peerSnapshots;
  private final InetAddress address;
  public final ToClientMessageSink messageSink;

  public ActivePlayer(PlayerCharacter character, InetAddress address) {
    this.character = character;
    this.peerSnapshots = synchronizedMap(new HashMap<PlayerCharacter, MotionSnapshot>());
    this.address = address;
    messageSink = new ToClientMessageSink(address);
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
