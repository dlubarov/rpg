package rpg.util;

public final class HashCodeBuilder {
  private int sum = 0;

  public HashCodeBuilder append(Object o) {
    sum = 31 * sum + o.hashCode();
    return this;
  }

  public int toHashCode() {
    return sum;
  }
}
