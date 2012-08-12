package rpg.client.gfx.widget;

import rpg.util.ToStringBuilder;

public class Bounds {
  private final int x1, y1, x2, y2;

  public Bounds(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  public int x1() {
    return x1;
  }

  public int y1() {
    return y1;
  }

  public int x2() {
    return x2;
  }

  public int y2() {
    return y2;
  }

  public int w() {
    return x2 - x1;
  }

  public int h() {
    return y2 - y1;
  }

  public boolean contains(int x, int y) {
    return x >= x1 && x < x2
        && y >= y1 && y < y2;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("x1", x1)
        .append("y1", y1)
        .append("x2", x2)
        .append("y2", y2)
        .toString();
  }
}
