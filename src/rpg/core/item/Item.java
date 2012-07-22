package rpg.core.item;

public abstract class Item {
  public final String name;
  public final double weight;

  protected Item(String name, double weight) {
    this.name = name;
    this.weight = weight;
  }
}
