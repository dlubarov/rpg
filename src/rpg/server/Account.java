package rpg.server;

import java.util.HashMap;
import java.util.Map;
import rpg.util.StringUtil;
import rpg.util.ToStringBuilder;

public class Account {
  public final int id;
  public final String email, password;
  private final Map<String, PlayerCharacter> characters;

  public Account(String email, String password) {
    this.id = AccountManager.getNextId();
    this.email = email;
    this.password = password;
    characters = new HashMap<String, PlayerCharacter>();
    AccountManager.register(this);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("email", email)
        .append("password", StringUtil.mask(password))
        .append("characters", characters.size())
        .toString();
  }
}
