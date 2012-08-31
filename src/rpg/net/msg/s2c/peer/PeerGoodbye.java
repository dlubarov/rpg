package rpg.net.msg.s2c.peer;

import java.util.List;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.ListSerializer;
import rpg.util.serialization.Serializer;

/**
 * A {@link rpg.net.msg.Message} informing the client of one or more peers which the server no longer
 * considers neighbors of the client. The server will stop sending motion updates for these
 * peers, so the client should stop rendering them.
 */
public class PeerGoodbye {
  public final List<Integer> ids;

  public PeerGoodbye(List<Integer> ids) {
    this.ids = ids;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("ids", ids)
        .toString();
  }

  public static final Serializer<PeerGoodbye> serializer =
      new Serializer<PeerGoodbye>() {
        @Override public void serialize(PeerGoodbye goodbye, ByteSink sink) {
          ListSerializer.integerListSerializer.serialize(goodbye.ids, sink);
        }

        @Override public PeerGoodbye deserialize(ByteSource source) {
          return new PeerGoodbye(ListSerializer.integerListSerializer.deserialize(source));
        }
      };
}
