package rpg.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ServerPlayer {
  public final Account account;
  private Map<Account, MotionSnapshot> peerSnapshots;

  public ServerPlayer(Account account) {
    this.account = account;
    this.peerSnapshots = Collections.synchronizedMap(new HashMap<Account, MotionSnapshot>());
  }

  public boolean inView(Account account) {
    return peerSnapshots.containsKey(account);
  }

  public double errorInViewOf(Account account) {
    MotionSnapshot view = peerSnapshots.get(account);
    return errorFor(
        view.position.euclideanDistanceTo(account.getPos()),
        view.velocity.euclideanDistanceTo(account.getVel()),
        view.direction.euclideanDistanceTo(account.getDir()));
  }

  private static double errorFor(double posErr, double velErr, double dirErr) {
    return posErr + velErr + dirErr;
  }
}
