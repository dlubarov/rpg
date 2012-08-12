package rpg.msg.s2c;

import rpg.core.CharacterSummary;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

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
