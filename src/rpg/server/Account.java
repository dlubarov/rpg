package rpg.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import rpg.core.CharacterSummary;
import rpg.util.StringUtil;
import rpg.util.ToStringBuilder;

public class Account {
  private static final AtomicInteger idCounter = new AtomicInteger();

  public final int id;
  public final String email, password;
  private final Map<String, PlayerCharacter> characters;

  public Account(String email, String password) {
    this.id = idCounter.getAndIncrement();
    this.email = email;
    this.password = password;
    characters = new HashMap<String, PlayerCharacter>();
    AccountManager.register(this);
  }

  public CharacterSummary[] getCharacterSummaries() {
    CharacterSummary[] summaries = new CharacterSummary[characters.size()];
    int i = 0;
    for (PlayerCharacter character : characters.values())
      summaries[i++] = new CharacterSummary(character.id, character.name, character.combatClass);
    return summaries;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("email", email)
        .append("password", StringUtil.mask(password))
        .append("characters", characters.size())
        .toString();
  }
}
