package rpg.net.msg.s2c;

import rpg.game.CharacterSummary;
import rpg.net.msg.Message;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.Serializer;

public class NewCharacterSuccessMessage extends Message {
  public final CharacterSummary characterSummary;

  public NewCharacterSuccessMessage(CharacterSummary characterSummary) {
    super(MessageType.NEW_CHARACTER_SUCCESS, serializer);
    this.characterSummary = characterSummary;
  }

  @Override public String toString() {
    return new ToStringBuilder(this).toString();
  }

  public static final Serializer<NewCharacterSuccessMessage> serializer =
      new Serializer<NewCharacterSuccessMessage>() {
        @Override public void serialize(NewCharacterSuccessMessage msg, ByteSink sink) {
          CharacterSummary.serializer.serialize(msg.characterSummary, sink);
        }

        @Override public NewCharacterSuccessMessage deserialize(ByteSource source) {
          CharacterSummary characterSummary = CharacterSummary.serializer.deserialize(source);
          return new NewCharacterSuccessMessage(characterSummary);
        }
      };
}
