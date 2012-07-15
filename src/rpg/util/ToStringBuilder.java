package rpg.util;

public final class ToStringBuilder {
  private final StringBuilder sb;
  private boolean first = true;

  public ToStringBuilder(Object o) {
    sb = new StringBuilder(o.getClass().getSimpleName()).append('[');
  }

  public ToStringBuilder append(String fieldName, Object field) {
    if (first)
      first = false;
    else
      sb.append(", ");
    sb.append(fieldName).append('=').append(field);
    return this;
  }

  public String toString() {
    return sb.append(']').toString();
  }
}
