package rpg.core.item.equip.iron;

import rpg.core.item.cat.ChestEquipment;

public class IronChain extends ChestEquipment {
  public static final IronChain singleton = new IronChain();

  private IronChain() {
    super("iron chainmail", 10);
  }
}
