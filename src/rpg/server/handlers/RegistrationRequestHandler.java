package rpg.server.handlers;

import java.net.InetAddress;
import rpg.msg.c2s.RegistrationRequestMessage;
import rpg.msg.s2c.RegistrationAcceptanceMessage;
import rpg.msg.s2c.RegistrationErrorMessage;
import rpg.net.NetConfig;
import rpg.net.ToClientMessageSink;
import rpg.server.Account;
import rpg.server.AccountManager;

public class RegistrationRequestHandler extends Handler<RegistrationRequestMessage> {
  public static final RegistrationRequestHandler singleton = new RegistrationRequestHandler();

  private RegistrationRequestHandler() {}

  @Override
  public void handle(RegistrationRequestMessage msg, InetAddress sender) {
    Account account = new Account(msg.email, msg.password);
    RegistrationErrorMessage.Reason failureReason = getFailureReason(msg);
    if (failureReason == null) {
      AccountManager.register(account);
      // FIXME: Send
      RegistrationAcceptanceMessage acceptanceMessage = new RegistrationAcceptanceMessage();
      new ToClientMessageSink(sender).sendWithConfirmation(acceptanceMessage, 3);
    } else {
      RegistrationErrorMessage rejectionMsg = new RegistrationErrorMessage(failureReason);
      new ToClientMessageSink(sender).sendWithConfirmation(rejectionMsg, 3);
    }
  }

  private RegistrationErrorMessage.Reason getFailureReason(RegistrationRequestMessage msg) {
    // FIXME: Check version.

    if (msg.email.length() > NetConfig.EMAIL_MAX_LEN)
      return RegistrationErrorMessage.Reason.EMAIL_LONG;
    if (!isEmailValid(msg.email))
      return RegistrationErrorMessage.Reason.EMAIL_BAD_FORMAT;
    if (AccountManager.getAccountByEmail(msg.email) != null)
      return RegistrationErrorMessage.Reason.EMAIL_TAKEN;

    if (msg.password.length() < NetConfig.PASSWORD_MIN_LEN)
      return RegistrationErrorMessage.Reason.PASSWORD_SHORT;
    if (msg.password.length() > NetConfig.PASSWORD_MAX_LEN)
      return RegistrationErrorMessage.Reason.PASSWORD_LONG;
    if (isEasyPassword(msg.password, msg.email))
      return RegistrationErrorMessage.Reason.PASSWORD_EASY;

    return null;
  }

  private static boolean isEmailValid(String email) {
    // FIXME: Do proper email validation.
    return email.contains("@");
  }

  private static boolean isEasyPassword(String password, String email) {
    // TODO: Check for easy passwords, e.g. substrings of email.
    return false;
  }
}
