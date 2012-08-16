package rpg.game.item;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
  private final Map<Item, Integer> items;

  public Inventory(Item... initialItems) {
    items = new HashMap<Item, Integer>();
    for (Item item : initialItems)
      add(item);
  }

  public synchronized int getQuantity(Item item) {
    Integer quantity = items.get(item);
    return quantity == null ? 0 : quantity;
  }

  public synchronized void setQuantity(Item item, int quantity) {
    items.put(item, quantity);
  }

  public synchronized void add(Item item, int quantity) {
    setQuantity(item, getQuantity(item) + quantity);
  }

  public void add(Item item) {
    add(item, 1);
  }

  public synchronized boolean remove(Item item, int quantity) {
    int currentQuantity = getQuantity(item);
    if (currentQuantity < quantity)
      return false;
    setQuantity(item, currentQuantity - quantity);
    assert getQuantity(item) >= 0;
    return true;
  }

  public boolean remove(Item item) {
    return remove(item, 1);
  }
}
