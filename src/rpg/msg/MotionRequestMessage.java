package rpg.msg;

import rpg.math.Vector3;
import rpg.util.ToStringBuilder;

public class MotionRequestMessage extends Message {
  public final Vector3 destination;

  public MotionRequestMessage(Vector3 destination) {
    this.destination = destination;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("destination", destination)
        .toString();
  }
}
