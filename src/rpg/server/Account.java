package rpg.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import rpg.core.CharacterSummary;
import rpg.util.StringUtil;
import rpg.util.ToStringBuilder;

public class Account {
  private static final AtomicInteger idCounter = new AtomicInteger();

  static {
    // TODO: Remove hardcoded account.
    new Account("d@l.com", "abcdef");
  }

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

  public List<CharacterSummary> getCharacterSummaries() {
    List<CharacterSummary> summaries = new ArrayList<CharacterSummary>(characters.size());
    for (PlayerCharacter character : characters.values())
      summaries.add(new CharacterSummary(character.id, character.name, character.combatClass));
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
