package rpg.server.handlers;

import rpg.core.NetConfig;
import rpg.msg.c2s.RegistrationRequestMessage;
import rpg.msg.s2c.RegistrationErrorMessage;
import rpg.msg.Handler;
import rpg.server.Account;
import rpg.server.AccountManager;

public class RegistrationRequestHandler extends Handler<RegistrationRequestMessage> {
  @Override
  public void handle(RegistrationRequestMessage msg) {
    Account account = new Account(msg.username, msg.password);
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
    if (msg.username.length() < NetConfig.USERNAME_MIN_LEN)
      return RegistrationErrorMessage.Reason.USERNAME_SHORT;
    if (msg.username.length() > NetConfig.USERNAME_MAX_LEN)
      return RegistrationErrorMessage.Reason.USERNAME_LONG;
    if (msg.password.length() < NetConfig.PASSWORD_MIN_LEN)
      return RegistrationErrorMessage.Reason.PASSWORD_SHORT;
    if (msg.password.length() > NetConfig.PASSWORD_MAX_LEN)
      return RegistrationErrorMessage.Reason.PASSWORD_LONG;
    for (char c : msg.username.toCharArray())
      if (!NetConfig.validUsernameCharacter(c))
        return RegistrationErrorMessage.Reason.USERNAME_BAD_CHARS;
    if (AccountManager.getByUsername(msg.username) == null)
      return RegistrationErrorMessage.Reason.USERNAME_TAKEN;
    return null;
  }
}
