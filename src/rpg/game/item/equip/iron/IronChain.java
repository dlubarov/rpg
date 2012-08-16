package rpg.game.item.equip.iron;

import rpg.game.item.cat.ChestEquipment;

public class IronChain extends ChestEquipment {
  public static final IronChain singleton = new IronChain();

  private IronChain() {
    super("iron chainmail", 10);
  }
}
