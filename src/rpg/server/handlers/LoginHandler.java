package rpg.server.handlers;

import java.net.InetAddress;
import rpg.game.Info;
import rpg.net.ToClientMessageSink;
import rpg.net.msg.c2s.LoginMessage;
import rpg.net.msg.s2c.CharacterInfoMessage;
import rpg.net.msg.s2c.LoginErrorMessage;
import rpg.server.Account;
import rpg.server.AccountManager;

public class LoginHandler extends Handler<LoginMessage> {
  public static final LoginHandler singleton = new LoginHandler();

  private LoginHandler() {}

  @Override public void handle(LoginMessage msg, InetAddress sender) {
    if (!msg.version.equals(Info.versionParts)) {
      sendRejection(LoginErrorMessage.Reason.BAD_VERSION, sender);
      return;
    }
    if (msg.email.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_EMAIL, sender);
      return;
    }
    if (msg.password.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_PASSWORD, sender);
      return;
    }

    Account account = AccountManager.getAccountByEmail(msg.email);
    if (account == null) {
      sendRejection(LoginErrorMessage.Reason.BAD_EMAIL, sender);
      return;
    }
    if (!msg.password.equals(account.password)) {
      sendRejection(LoginErrorMessage.Reason.BAD_PASSWORD, sender);
      return;
    }

    // Successful login.
    AccountManager.noteLastAddress(account, sender);

    CharacterInfoMessage charInfoMsg = new CharacterInfoMessage(account.getCharacterSummaries());
    new ToClientMessageSink(sender).sendWithConfirmation(charInfoMsg, 3);
  }

  private void sendRejection(LoginErrorMessage.Reason reason, InetAddress sender) {
    LoginErrorMessage msg = new LoginErrorMessage(reason);
    new ToClientMessageSink(sender).sendWithConfirmation(msg, 3);
  }
}
