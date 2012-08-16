package rpg.game.item.equip.iron;

import rpg.game.item.cat.HeadEquipment;

public class IronHelm extends HeadEquipment {
  public static final IronHelm singleton = new IronHelm();

  private IronHelm() {
    super("iron helmet", 4);
  }
}
