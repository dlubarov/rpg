package rpg.net.msg.s2c.peer;

import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.IntegerSerializer;
import rpg.util.serialization.Serializer;

/**
 * A {@link rpg.net.msg.Message} informing the client of one or more peers which the server no longer
 * considers neighbors of the client. The server will stop sending motion updates for these
 * peers, so the client should stop rendering them.
 */
public class PeerGoodbye {
  public final Integer id;

  public PeerGoodbye(int id) {
    this.id = id;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .toString();
  }

  public static final Serializer<PeerGoodbye> serializer =
      new Serializer<PeerGoodbye>() {
        @Override public void serialize(PeerGoodbye goodbye, ByteSink sink) {
          IntegerSerializer.singleton.serialize(goodbye.id, sink);
        }

        @Override public PeerGoodbye deserialize(ByteSource source) {
          return new PeerGoodbye(IntegerSerializer.singleton.deserialize(source));
        }
      };
}
