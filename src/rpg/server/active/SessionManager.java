package rpg.server.active;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class SessionManager {
  private SessionManager() {}

  private static final Map<Long, Session> sessionsByID;

  static {
    sessionsByID = Collections.synchronizedMap(new HashMap<Long, Session>());
  }

  public static Session getByID(long id) {
    return sessionsByID.get(id);
  }

  public static void register(Session session) {
    sessionsByID.put(session.id, session);
  }
}
