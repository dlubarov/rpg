package rpg.server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import rpg.game.MotionState;
import rpg.net.ToClientMessageSink;

import static java.util.Collections.synchronizedMap;

public final class ActivePlayer {
  public final PlayerCharacter character;
  private final Map<PlayerCharacter, MotionState> peerSnapshots;
  public final InetAddress address;
  public final ToClientMessageSink messageSink;

  public ActivePlayer(PlayerCharacter character, InetAddress address) {
    this.character = character;
    this.peerSnapshots = synchronizedMap(new HashMap<PlayerCharacter, MotionState>());
    this.address = address;
    messageSink = new ToClientMessageSink(address);

    AccountManager.login(this);
  }

  public boolean inView(PlayerCharacter peer) {
    return peerSnapshots.containsKey(peer);
  }

  public double errorInViewOf(PlayerCharacter character) {
    MotionState view = peerSnapshots.get(character);
    return view.errorComparedTo(character.getMotionState());
  }

  public void logout() {
    AccountManager.logout(this);
  }
}
