package rpg.ser;

import rpg.math.Vector3;

public class Vector3Serializer extends Serializer<Vector3> {
  public static final Vector3Serializer singleton = new Vector3Serializer();

  private Vector3Serializer() {}

  @Override
  public void serialize(Vector3 v, ByteSink sink) {
    sink.giveAll(DoubleSerializer.singleton.serialize(v.x));
    sink.giveAll(DoubleSerializer.singleton.serialize(v.y));
    sink.giveAll(DoubleSerializer.singleton.serialize(v.z));
  }

  @Override
  public Vector3 deserialize(ByteSource source) {
    return new Vector3(
        DoubleSerializer.singleton.deserialize(source),
        DoubleSerializer.singleton.deserialize(source),
        DoubleSerializer.singleton.deserialize(source));
  }
}
