package rpg.server.handlers;

import rpg.game.Info;
import rpg.net.ToClientMessageSink;
import rpg.net.msg.c2s.LoginMessage;
import rpg.net.msg.s2c.CharacterInfoMessage;
import rpg.net.msg.s2c.LoginErrorMessage;
import rpg.server.account.Account;
import rpg.server.account.AccountManager;
import rpg.server.active.Session;

public class LoginHandler extends Handler<LoginMessage> {
  public static final LoginHandler singleton = new LoginHandler();

  private LoginHandler() {}

  @Override public void handle(LoginMessage msg, Session clientSession) {
    if (!msg.version.equals(Info.versionParts)) {
      sendRejection(LoginErrorMessage.Reason.BAD_VERSION, clientSession);
      return;
    }
    if (msg.email.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_EMAIL, clientSession);
      return;
    }
    if (msg.password.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_PASSWORD, clientSession);
      return;
    }

    Account account = AccountManager.getAccountByEmail(msg.email);
    if (account == null) {
      sendRejection(LoginErrorMessage.Reason.BAD_EMAIL, clientSession);
      return;
    }
    if (!msg.password.equals(account.password)) {
      sendRejection(LoginErrorMessage.Reason.BAD_PASSWORD, clientSession);
      return;
    }

    // Successful login.
    clientSession.account = account;
    CharacterInfoMessage charInfoMsg = new CharacterInfoMessage(account.getCharacterSummaries());
    new ToClientMessageSink(clientSession).sendWithConfirmation(charInfoMsg, 3);
  }

  private void sendRejection(LoginErrorMessage.Reason reason, Session clientSession) {
    LoginErrorMessage msg = new LoginErrorMessage(reason);
    new ToClientMessageSink(clientSession).sendWithConfirmation(msg, 3);
  }
}
