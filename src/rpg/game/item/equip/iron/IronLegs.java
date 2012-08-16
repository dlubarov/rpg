package rpg.game.item.equip.iron;

import rpg.game.item.cat.LegEquipment;

public class IronLegs extends LegEquipment {
  public static final IronLegs singleton = new IronLegs();

  private IronLegs() {
    super("iron legs", 8);
  }
}
