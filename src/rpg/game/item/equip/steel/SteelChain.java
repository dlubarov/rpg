package rpg.game.item.equip.steel;

import rpg.game.item.cat.ChestEquipment;

public class SteelChain extends ChestEquipment {
  public static final SteelChain singleton = new SteelChain();

  private SteelChain() {
    super("steel chain", 10);
  }
}
