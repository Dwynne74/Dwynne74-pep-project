package Service;

import DAO.AccountDAO;
import Model.Account;
import Model.Message;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if(accountDAO.usernameAlreadyExist(account.getUsername()) == false && account.getUsername().length() > 0 
        && account.getPassword().length() > 3){
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }
    }

    public Message getMessageByUser(String username) {
        if(accountDAO.usernameAlreadyExist(username) == false) {
            return null;
        }
        return accountDAO.getMessageByUser(username);
    }

    public Account getAccountVerified(String username, String password) {
        return accountDAO.getAccountVerified(username, password);
    }
}
