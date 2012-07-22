package rpg.client.mode;

import rpg.core.CharacterSummary;

public class CharacterSelectMode extends Mode {
  private final CharacterSummary[] options;

  public CharacterSelectMode(CharacterSummary[] options) {
    this.options = options;
  }

  @Override
  public void render() {
    // FIXME
  }
}
