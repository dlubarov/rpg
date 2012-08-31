package rpg.server.active;

import java.net.InetAddress;
import rpg.server.account.Account;
import rpg.util.ToStringBuilder;

public final class Session {
  public final long id;
  public final InetAddress address;
  public final int port;

  public Account account = null;
  public ActivePlayer player = null;
  private boolean alive = true;

  public Session(long id, InetAddress address, int port) {
    this.id = id;
    this.address = address;
    this.port = port;

    SessionManager.register(this);
  }

  public boolean isAlive() {
    return alive;
  }

  public void terminate() {
    alive = false;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("address", "address")
        .append("port", port)
        .append("account", account)
        .append("player", player)
        .append("alive", alive)
        .toString();
  }
}
