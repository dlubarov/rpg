package rpg.msg;

import rpg.util.ToStringBuilder;

public class AccountCreationMessage extends Message {
  public final String username, password;

  public AccountCreationMessage(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("username", username)
        .append("password", password)
        .toString();
  }
}
