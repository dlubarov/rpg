package rpg.server.handlers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import rpg.game.Info;
import rpg.net.NetConfig;
import rpg.net.ToClientMessageSink;
import rpg.net.msg.c2s.RegistrationMessage;
import rpg.net.msg.s2c.RegistrationAcceptanceMessage;
import rpg.net.msg.s2c.RegistrationErrorMessage;
import rpg.server.Account;
import rpg.server.AccountManager;
import rpg.server.Session;

public class RegistrationHandler extends Handler<RegistrationMessage> {
  public static final RegistrationHandler singleton = new RegistrationHandler();

  // This is far from perfect, but I don't really care...
  private static final Pattern emailPattern = Pattern.compile(
      "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{1,})$");

  private static final Set<String> commonPasswords = new HashSet<String>(Arrays.asList(
      "password", "password1", "secret", "qwerty", "qazqaz"
  ));

  private RegistrationHandler() {}

  @Override public void handle(RegistrationMessage msg, Session clientSession) {
    RegistrationErrorMessage.Reason failureReason = getFailureReason(msg);
    if (failureReason == null) {
      Account account = new Account(msg.email, msg.password);
      AccountManager.register(account);
      clientSession.account = account;
      RegistrationAcceptanceMessage acceptanceMessage = new RegistrationAcceptanceMessage();
      new ToClientMessageSink(clientSession).sendWithConfirmation(acceptanceMessage, 3);
    } else {
      RegistrationErrorMessage rejectionMsg = new RegistrationErrorMessage(failureReason);
      new ToClientMessageSink(clientSession).sendWithConfirmation(rejectionMsg, 3);
    }
  }

  private RegistrationErrorMessage.Reason getFailureReason(RegistrationMessage msg) {
    // Validate version.
    if (!msg.version.equals(Info.versionParts))
      return RegistrationErrorMessage.Reason.BAD_VERSION;

    // Validate email.
    if (msg.email.isEmpty())
      return RegistrationErrorMessage.Reason.EMAIL_MISSING;
    if (msg.email.length() > NetConfig.EMAIL_MAX_LEN)
      return RegistrationErrorMessage.Reason.EMAIL_LONG;
    if (!isEmailValid(msg.email))
      return RegistrationErrorMessage.Reason.EMAIL_BAD_FORMAT;
    if (AccountManager.getAccountByEmail(msg.email) != null)
      return RegistrationErrorMessage.Reason.EMAIL_TAKEN;

    // Validate password.
    if (msg.password.isEmpty())
      return RegistrationErrorMessage.Reason.PASSWORD_MISSING;
    if (msg.password.length() < NetConfig.PASSWORD_MIN_LEN)
      return RegistrationErrorMessage.Reason.PASSWORD_SHORT;
    if (msg.password.length() > NetConfig.PASSWORD_MAX_LEN)
      return RegistrationErrorMessage.Reason.PASSWORD_LONG;
    if (isEasyPassword(msg.password, msg.email))
      return RegistrationErrorMessage.Reason.PASSWORD_EASY;

    return null;
  }

  private static boolean isEmailValid(String email) {
    return emailPattern.matcher(email).matches();
  }

  private static boolean isEasyPassword(String password, String email) {
    email = email.toLowerCase();
    password = password.toLowerCase();
    return email.contains(password) || commonPasswords.contains(password);
  }
}
