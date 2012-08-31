package rpg.net.msg.s2c.peer;

import rpg.game.MotionState;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;

/**
 * A {@link rpg.net.msg.Message} informing the client of one or more nearby peers which the client was not
 * previously aware of.
 */
public class PeerUpdate {
  public final Integer id;
  public final MotionState motionState;

  public PeerUpdate(int id, MotionState motionState) {
    this.id = id;
    this.motionState = motionState;
  }

  public static final Serializer<PeerUpdate> serializer =
      new Serializer<PeerUpdate>() {
        @Override public void serialize(PeerUpdate update, ByteSink sink) {
          IntegerSerializer.singleton.serialize(update.id, sink);
          MotionState.serializer.serialize(update.motionState, sink);
        }

        @Override public PeerUpdate deserialize(ByteSource source) {
          return new PeerUpdate(
              IntegerSerializer.singleton.deserialize(source),
              MotionState.serializer.deserialize(source));
        }
      };

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("motionState", motionState)
        .toString();
  }
}
