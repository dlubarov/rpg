package rpg.core.item;

public class Coin extends Item {
  public static final Coin singleton = new Coin();

  private Coin() {
    super("coin", 2.0 / 1000);
  }
}
