package rpg.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import rpg.game.CharacterSummary;
import rpg.util.Logger;
import rpg.util.StringUtil;
import rpg.util.ToStringBuilder;

public class Account {
  private static final AtomicInteger idCounter = new AtomicInteger();

  public final int id;
  public final String email, password;
  private final Set<PlayerCharacter> characters;

  public Account(String email, String password) {
    this.id = idCounter.getAndIncrement();
    this.email = email;
    this.password = password;
    characters = new HashSet<PlayerCharacter>();

    AccountManager.register(this);
    Logger.info("Created new account: %s.", this);
  }

  void addCharacter(PlayerCharacter character) {
    characters.add(character);
  }

  public List<CharacterSummary> getCharacterSummaries() {
    List<CharacterSummary> summaries = new ArrayList<CharacterSummary>(characters.size());
    for (PlayerCharacter character : characters)
      summaries.add(new CharacterSummary(character));
    return summaries;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("email", email)
        .append("password", StringUtil.mask(password))
        .append("characters", characters.size())
        .toString();
  }
}
