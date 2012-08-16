package rpg.core.item.equip.iron;

import rpg.core.item.cat.HeadEquipment;

public class IronHelm extends HeadEquipment {
  public static final IronHelm singleton = new IronHelm();

  private IronHelm() {
    super("iron helmet", 4);
  }
}
