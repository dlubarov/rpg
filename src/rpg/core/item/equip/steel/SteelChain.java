package rpg.core.item.equip.steel;

import rpg.core.item.cat.ChestEquipment;

public class SteelChain extends ChestEquipment {
  public static final SteelChain singleton = new SteelChain();

  private SteelChain() {
    super("steel chain", 10);
  }
}
