import Controller.SocialMediaController;
import DAO.AccountDAO;
import Model.Account;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);

        AccountDAO accountDAO = new AccountDAO();
        Account testAccount = new Account("testuser1", "passwor");
        // Testing user duplicate
        // System.out.println(accountDAO.usernameAlreadyExist("testuser1"));

        // Testing creating account
        // Account account = new Account("Denzel", "Password");
        // System.out.println(accountDAO.insertAccount(account));

        // Testing get message with user
        // System.out.println(accountDAO.getMessageByUser("testuser1"));

        // Test verify account
        System.out.println(accountDAO.getAccountVerified(testAccount));

    }
}
