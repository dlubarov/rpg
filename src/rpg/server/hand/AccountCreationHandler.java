package rpg.server.hand;

import rpg.core.NetConfig;
import rpg.msg.AccountCreationErrorMessage;
import rpg.msg.AccountCreationMessage;
import rpg.msg.Handler;
import rpg.server.Account;
import rpg.server.AccountManager;

public class AccountCreationHandler extends Handler<AccountCreationMessage> {
  @Override
  public void handle(AccountCreationMessage msg) {
    Account account = new Account(msg.username, msg.password);
    AccountCreationErrorMessage.Reason failureReason = getFailureReason(msg);
    if (failureReason == null) {
      AccountManager.register(account);
      // FIXME send welcome
    } else {
      AccountCreationErrorMessage rejectionMsg = new AccountCreationErrorMessage(failureReason);
      // FIXME send rejection
    }
  }

  private AccountCreationErrorMessage.Reason getFailureReason(AccountCreationMessage msg) {
    if (msg.username.length() < NetConfig.USERNAME_MIN_LEN)
      return AccountCreationErrorMessage.Reason.USERNAME_SHORT;
    if (msg.username.length() > NetConfig.USERNAME_MAX_LEN)
      return AccountCreationErrorMessage.Reason.USERNAME_LONG;
    if (msg.password.length() < NetConfig.PASSWORD_MIN_LEN)
      return AccountCreationErrorMessage.Reason.PASSWORD_SHORT;
    if (msg.password.length() > NetConfig.PASSWORD_MAX_LEN)
      return AccountCreationErrorMessage.Reason.PASSWORD_LONG;
    for (char c : msg.username.toCharArray())
      if (!NetConfig.validUsernameCharacter(c))
        return AccountCreationErrorMessage.Reason.USERNAME_BAD_CHARS;
    if (AccountManager.getByUsername(msg.username) == null)
      return AccountCreationErrorMessage.Reason.USERNAME_TAKEN;
    return null;
  }
}
