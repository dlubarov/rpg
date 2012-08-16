package rpg.net.msg.c2s;

import rpg.game.MotionState;
import rpg.net.msg.Message;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.Serializer;

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
