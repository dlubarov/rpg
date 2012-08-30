package rpg.server;

import java.net.InetAddress;
import rpg.util.ToStringBuilder;

public class Session {
  public final long id;
  public final InetAddress address;
  public final int port;

  public Account account = null;
  public ActivePlayer player = null;

  public Session(long id, InetAddress address, int port) {
    this.id = id;
    this.address = address;
    this.port = port;

    SessionManager.register(this);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("address", "address")
        .append("port", port)
        .append("account", account)
        .append("player", player)
        .toString();
  }
}
