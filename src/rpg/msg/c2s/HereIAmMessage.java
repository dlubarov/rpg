package rpg.msg.c2s;

import rpg.core.MotionState;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

/**
 * Informs the server of the player's latest position.
 */
public class HereIAmMessage extends Message {
  public final MotionState motionState;

  public HereIAmMessage(MotionState motionState) {
    super(MessageType.HERE_I_AM, serializer);
    this.motionState = motionState;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("motionState", motionState)
        .toString();
  }

  public static final Serializer<HereIAmMessage> serializer = new Serializer<HereIAmMessage>() {
    @Override public void serialize(HereIAmMessage msg, ByteSink sink) {
      MotionState.serializer.serialize(msg.motionState, sink);
    }

    @Override public HereIAmMessage deserialize(ByteSource source) {
      return new HereIAmMessage(MotionState.serializer.deserialize(source));
    }
  };
}
