package rpg.server.handlers;

import java.net.InetAddress;
import rpg.msg.c2s.LoginRequestMessage;
import rpg.msg.s2c.CharacterInfoMessage;
import rpg.msg.s2c.LoginErrorMessage;
import rpg.server.Account;
import rpg.server.AccountManager;

public class LoginRequestHandler extends Handler<LoginRequestMessage> {
  public static final LoginRequestHandler singleton = new LoginRequestHandler();

  private LoginRequestHandler() {}

  @Override
  public void handle(LoginRequestMessage msg, InetAddress sender) {
    // FIXME: Check version.

    if (msg.email.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_USERNAME_OR_EMAIL);
      return;
    }
    if (msg.password.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_PASSWORD);
      return;
    }

    Account account = AccountManager.getAccountByEmail(msg.email);
    if (account == null) {
      sendRejection(LoginErrorMessage.Reason.BAD_USERNAME_OR_EMAIL);
      return;
    }
    if (!msg.password.equals(account.password)) {
      sendRejection(LoginErrorMessage.Reason.BAD_PASSWORD);
      return;
    }

    CharacterInfoMessage charInfoMsg = new CharacterInfoMessage(account.getCharacterSummaries());
    // FIXME: Send character info.
  }

  private void sendRejection(LoginErrorMessage.Reason reason) {
    LoginErrorMessage msg = new LoginErrorMessage(reason);
    // FIXME: Send rejection message.
  }
}
