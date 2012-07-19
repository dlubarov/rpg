package rpg.server.handlers;

import java.net.InetAddress;
import rpg.msg.c2s.LoginRequestMessage;
import rpg.msg.c2s.RegistrationRequestMessage;
import rpg.msg.s2c.LoginErrorMessage;
import rpg.msg.s2c.RegistrationErrorMessage;
import rpg.net.NetConfig;
import rpg.server.Account;
import rpg.server.AccountManager;

public class LoginRequestHandler extends Handler<LoginRequestMessage> {
  public static final LoginRequestHandler singleton = new LoginRequestHandler();

  private LoginRequestHandler() {}

  @Override
  public void handle(LoginRequestMessage msg, InetAddress sender) {
    if (msg.usernameOrEmail.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_USERNAME_OR_EMAIL);
      return;
    }
    if (msg.password.isEmpty()) {
      sendRejection(LoginErrorMessage.Reason.MISSING_PASSWORD);
      return;
    }

    boolean isEmail = msg.usernameOrEmail.contains("@");
    Account account = isEmail
        ? AccountManager.getAccountByEmail(msg.usernameOrEmail)
        : AccountManager.getAccountByUsername(msg.usernameOrEmail);

    if (account == null) {
      sendRejection(LoginErrorMessage.Reason.BAD_USERNAME_OR_EMAIL);
      return;
    }

    if (!msg.password.equals(account.password)) {
      sendRejection(LoginErrorMessage.Reason.BAD_PASSWORD);
      return;
    }

    // FIXME: Send welcome message.
  }

  private void sendRejection(LoginErrorMessage.Reason reason) {
    LoginErrorMessage msg = new LoginErrorMessage(reason);
    // FIXME: Send rejection message.
  }
}
