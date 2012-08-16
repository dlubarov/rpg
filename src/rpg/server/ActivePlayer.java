package rpg.server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import rpg.game.MotionState;
import rpg.net.ToClientMessageSink;

import static java.util.Collections.synchronizedMap;

public final class ActivePlayer {
  private final PlayerCharacter character;
  private final Map<PlayerCharacter, MotionState> peerSnapshots;
  private final InetAddress address;
  public final ToClientMessageSink messageSink;

  public ActivePlayer(PlayerCharacter character, InetAddress address) {
    this.character = character;
    this.peerSnapshots = synchronizedMap(new HashMap<PlayerCharacter, MotionState>());
    this.address = address;
    messageSink = new ToClientMessageSink(address);
  }

  public boolean inView(PlayerCharacter peer) {
    return peerSnapshots.containsKey(peer);
  }

  public double errorInViewOf(PlayerCharacter character) {
    MotionState view = peerSnapshots.get(character);
    return errorFor(
        view.position.euclideanDistanceTo(character.getMotionState().position),
        view.velocity.euclideanDistanceTo(character.getMotionState().velocity),
        view.direction.euclideanDistanceTo(character.getMotionState().direction));
  }

  private static double errorFor(double posErr, double velErr, double dirErr) {
    return posErr + velErr + dirErr;
  }
}
