package rpg.server.handlers;

import java.net.InetAddress;
import rpg.net.NetConfig;
import rpg.msg.c2s.RegistrationRequestMessage;
import rpg.msg.s2c.RegistrationErrorMessage;
import rpg.server.Account;
import rpg.server.AccountManager;

public class RegistrationRequestHandler extends Handler<RegistrationRequestMessage> {
  public static final RegistrationRequestHandler singleton = new RegistrationRequestHandler();

  private RegistrationRequestHandler() {}

  @Override
  public void handle(RegistrationRequestMessage msg, InetAddress sender) {
    Account account = new Account(msg.email, msg.username, msg.password);
    RegistrationErrorMessage.Reason failureReason = getFailureReason(msg);
    if (failureReason == null) {
      AccountManager.register(account);
      // FIXME send welcome
    } else {
      RegistrationErrorMessage rejectionMsg = new RegistrationErrorMessage(failureReason);
      // FIXME send rejection
    }
  }

  private RegistrationErrorMessage.Reason getFailureReason(RegistrationRequestMessage msg) {
    if (!isEmailValid(msg.email))
      return RegistrationErrorMessage.Reason.EMAIL_BAD_FORMAT;
    if (AccountManager.getAccountByEmail(msg.email) != null)
      return RegistrationErrorMessage.Reason.EMAIL_TAKEN;
    if (msg.username.length() < NetConfig.USERNAME_MIN_LEN)
      return RegistrationErrorMessage.Reason.USERNAME_SHORT;
    if (msg.username.length() > NetConfig.USERNAME_MAX_LEN)
      return RegistrationErrorMessage.Reason.USERNAME_LONG;
    for (char c : msg.username.toCharArray())
      if (!NetConfig.validUsernameCharacter(c))
        return RegistrationErrorMessage.Reason.USERNAME_BAD_CHARS;
    if (AccountManager.getAccountByUsername(msg.username) != null)
      return RegistrationErrorMessage.Reason.USERNAME_TAKEN;
    if (msg.password.length() < NetConfig.PASSWORD_MIN_LEN)
      return RegistrationErrorMessage.Reason.PASSWORD_SHORT;
    if (msg.password.length() > NetConfig.PASSWORD_MAX_LEN)
      return RegistrationErrorMessage.Reason.PASSWORD_LONG;
    return null;
  }

  private static boolean isEmailValid(String email) {
    // FIXME: Do proper email validation.
    return email.contains("@");
  }
}
