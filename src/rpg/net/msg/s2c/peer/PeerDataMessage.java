package rpg.net.msg.s2c.peer;

import java.util.List;
import rpg.net.msg.MessageType;
import rpg.net.msg.s2c.ServerToClientMessage;
import rpg.util.ToStringBuilder;
import rpg.util.serialization.ByteSink;
import rpg.util.serialization.ByteSource;
import rpg.util.serialization.ListSerializer;
import rpg.util.serialization.Serializer;

public class PeerDataMessage extends ServerToClientMessage {
  public final List<PeerIntroduction> introdoctions;
  public final List<PeerGoodbye> goodbyes;
  public final List<PeerUpdate> updates;

  public PeerDataMessage(
      List<PeerIntroduction> introdoctions,
      List<PeerGoodbye> goodbyes,
      List<PeerUpdate> updates) {
    super(MessageType.PEER_DATA, serializer);
    this.introdoctions = introdoctions;
    this.goodbyes = goodbyes;
    this.updates = updates;
  }

  public static final Serializer<PeerDataMessage> serializer = new Serializer<PeerDataMessage>() {
    private final Serializer<List<PeerIntroduction>> introductionsSerializer =
        new ListSerializer<PeerIntroduction>(PeerIntroduction.serializer);
    private final Serializer<List<PeerGoodbye>> goodbyesSerializer =
        new ListSerializer<PeerGoodbye>(PeerGoodbye.serializer);
    private final Serializer<List<PeerUpdate>> updatesSerializer =
        new ListSerializer<PeerUpdate>(PeerUpdate.serializer);

    @Override public void serialize(PeerDataMessage msg, ByteSink sink) {
      introductionsSerializer.serialize(msg.introdoctions, sink);
      goodbyesSerializer.serialize(msg.goodbyes, sink);
      updatesSerializer.serialize(msg.updates, sink);
    }

    @Override public PeerDataMessage deserialize(ByteSource source) {
      return new PeerDataMessage(
          introductionsSerializer.deserialize(source),
          goodbyesSerializer.deserialize(source),
          updatesSerializer.deserialize(source));
    }
  };

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("introductions", introdoctions)
        .append("goodbyes", goodbyes)
        .append("updates", updates)
        .toString();
  }
}
