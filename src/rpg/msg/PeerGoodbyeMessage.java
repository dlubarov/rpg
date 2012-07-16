package rpg.msg;

import rpg.ser.ArraySerializer;
import rpg.ser.ByteSink;
import rpg.ser.ByteSource;
import rpg.ser.IntegerSerializer;
import rpg.ser.Serializer;

public class PeerGoodbyeMessage extends Message {
  public final Integer[] ids;

  public PeerGoodbyeMessage(Integer[] ids) {
    this.ids = ids;
  }

  public static final Serializer<PeerGoodbyeMessage> serializer =
      new Serializer<PeerGoodbyeMessage>() {
    private Serializer<Integer[]> arraySerializer =
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
