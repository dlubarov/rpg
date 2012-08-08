package rpg.server.handlers;

import java.net.InetAddress;
import rpg.core.Info;
import rpg.msg.c2s.LoginMessage;
import rpg.msg.s2c.CharacterInfoMessage;
import rpg.msg.s2c.LoginErrorMessage;
import rpg.net.ToClientMessageSink;
import rpg.server.Account;
import rpg.server.AccountManager;

public class LoginRequestHandler extends Handler<LoginMessage> {
  public static final LoginRequestHandler singleton = new LoginRequestHandler();

  private LoginRequestHandler() {}

  @Override
  public void handle(LoginMessage msg, InetAddress sender) {
    if (!msg.version.equals(Info.versionParts)) {
      sendRejection(LoginErrorMessage.Reason.BAD_VERSION, sender);
      return;
    }
    if (msg.email.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_USERNAME_OR_EMAIL, sender);
      return;
    }
    if (msg.password.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_PASSWORD, sender);
      return;
    }

    Account account = AccountManager.getAccountByEmail(msg.email);
    if (account == null) {
      sendRejection(LoginErrorMessage.Reason.BAD_USERNAME_OR_EMAIL, sender);
      return;
    }
    if (!msg.password.equals(account.password)) {
      sendRejection(LoginErrorMessage.Reason.BAD_PASSWORD, sender);
      return;
    }

    CharacterInfoMessage charInfoMsg = new CharacterInfoMessage(account.getCharacterSummaries());
    new ToClientMessageSink(sender).sendWithConfirmation(charInfoMsg, 3);
  }

  private void sendRejection(LoginErrorMessage.Reason reason, InetAddress sender) {
    LoginErrorMessage msg = new LoginErrorMessage(reason);
    new ToClientMessageSink(sender).sendWithConfirmation(msg, 3);
  }
}
