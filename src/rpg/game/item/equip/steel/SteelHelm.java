package rpg.game.item.equip.steel;

import rpg.game.item.cat.HeadEquipment;

public class SteelHelm extends HeadEquipment {
  public static final SteelHelm singleton = new SteelHelm();

  private SteelHelm() {
    super("steel helmet", 4);
  }
}
