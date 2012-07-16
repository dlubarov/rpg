package rpg.msg;

import rpg.ser.ByteSink;
import rpg.ser.ByteSource;
import rpg.ser.Serializer;
import rpg.ser.StringSerializer;
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

  public static final Serializer<AccountCreationMessage> serializer =
      new Serializer<AccountCreationMessage>() {
    @Override
    public void serialize(AccountCreationMessage msg, ByteSink sink) {
      StringSerializer.singleton.serialize(msg.username, sink);
      StringSerializer.singleton.serialize(msg.password, sink);
    }

    @Override
    public AccountCreationMessage deserialize(ByteSource source) {
      return new AccountCreationMessage(
          StringSerializer.singleton.deserialize(source),
          StringSerializer.singleton.deserialize(source));
    }
  };
}
