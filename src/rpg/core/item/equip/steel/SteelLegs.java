package rpg.core.item.equip.steel;

import rpg.core.item.cat.LegEquipment;

public class SteelLegs extends LegEquipment {
  public static final SteelLegs singleton = new SteelLegs();

  private SteelLegs() {
    super("steel legs", 8);
  }
}
