package rpg.net.msg.s2c;

import rpg.net.NetConfig;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;

public class NewCharacterErrorMessage extends ServerToClientMessage {
  public final Reason reason;

  public NewCharacterErrorMessage(Reason reason) {
    super(MessageType.NEW_CHARACTER_ERROR, serializer);
    this.reason = reason;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("reason", reason)
        .toString();
  }

  public static final Serializer<NewCharacterErrorMessage> serializer =
      new Serializer<NewCharacterErrorMessage>() {
        @Override public void serialize(NewCharacterErrorMessage msg, ByteSink sink) {
          IntegerSerializer.singleton.serialize(msg.reason.ordinal(), sink);
        }

        @Override public NewCharacterErrorMessage deserialize(ByteSource source) {
          int ordinal = IntegerSerializer.singleton.deserialize(source);
          return new NewCharacterErrorMessage(Reason.values()[ordinal]);
        }
      };

  public static enum Reason {
    NAME_MISSING("You must enter a character name."),
    NAME_SHORT("Character names must be at least " + NetConfig.NAME_MIN_LEN + " characters long"),
    NAME_LONG("Character names cannot exceed " + NetConfig.NAME_MAX_LEN + " characters in length."),
    NAME_ILLEGAL("Character names can contain only letters, numbers and underscores."),
    NAME_TAKEN("That character name is already taken. Please choose another.");

    public final String message;

    private Reason(String message) {
      this.message = message;
    }
  }
}
