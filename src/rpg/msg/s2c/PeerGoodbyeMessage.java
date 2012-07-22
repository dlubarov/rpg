package rpg.msg.s2c;

import java.util.Arrays;
import rpg.msg.Message;
import rpg.msg.MessageType;
import rpg.serialization.ArraySerializer;
import rpg.serialization.ByteSink;
import rpg.serialization.ByteSource;
import rpg.serialization.IntegerSerializer;
import rpg.serialization.Serializer;
import rpg.util.ToStringBuilder;

/**
 * A {@link Message} informing the client of one or more peers which the server no longer
 * considers neighbors of the client. The server will stop sending motion updates for these
 * peers, so the client should stop rendering them.
 */
public class PeerGoodbyeMessage extends Message {
  public final Integer[] ids;

  public PeerGoodbyeMessage(Integer[] ids) {
    super(MessageType.PEER_GOODBYE, serializer);
    this.ids = ids;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("ids", Arrays.toString(ids))
        .toString();
  }

  public static final Serializer<PeerGoodbyeMessage> serializer =
      new Serializer<PeerGoodbyeMessage>() {
    private final Serializer<Integer[]> arraySerializer =
        new ArraySerializer<Integer>(IntegerSerializer.singleton);

    @Override
    public void serialize(PeerGoodbyeMessage msg, ByteSink sink) {
      arraySerializer.serialize(msg.ids, sink);
    }

    @Override
    public PeerGoodbyeMessage deserialize(ByteSource source) {
      return new PeerGoodbyeMessage(arraySerializer.deserialize(source));
    }
  };
}
