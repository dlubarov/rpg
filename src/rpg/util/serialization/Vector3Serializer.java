package rpg.util.serialization;

import rpg.util.math.Vector3;

public class Vector3Serializer extends Serializer<Vector3> {
  public static final Vector3Serializer singleton = new Vector3Serializer();

  private Vector3Serializer() {}

  @Override public void serialize(Vector3 v, ByteSink sink) {
    DoubleSerializer.singleton.serialize(v.x, sink);
    DoubleSerializer.singleton.serialize(v.y, sink);
    DoubleSerializer.singleton.serialize(v.z, sink);
  }

  @Override public Vector3 deserialize(ByteSource source) {
    return new Vector3(
        DoubleSerializer.singleton.deserialize(source),
        DoubleSerializer.singleton.deserialize(source),
        DoubleSerializer.singleton.deserialize(source));
  }
}
