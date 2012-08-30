package rpg.net.msg.s2c;

import java.util.List;
import rpg.net.msg.Message;
import rpg.net.msg.MessageType;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.ListSerializer;
import rpg.util.serialization.Serializer;

/**
 * A {@link Message} informing the client of one or more peers which the server no longer
 * considers neighbors of the client. The server will stop sending motion updates for these
 * peers, so the client should stop rendering them.
 */
public class PeerGoodbyeMessage extends ServerToClientMessage {
  public final List<Integer> ids;

  public PeerGoodbyeMessage(List<Integer> ids) {
    super(MessageType.PEER_GOODBYE, serializer);
    this.ids = ids;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("ids", ids)
        .toString();
  }

  public static final Serializer<PeerGoodbyeMessage> serializer =
      new Serializer<PeerGoodbyeMessage>() {
        @Override public void serialize(PeerGoodbyeMessage msg, ByteSink sink) {
          ListSerializer.integerListSerializer.serialize(msg.ids, sink);
        }

        @Override public PeerGoodbyeMessage deserialize(ByteSource source) {
          return new PeerGoodbyeMessage(ListSerializer.integerListSerializer.deserialize(source));
        }
      };
}
