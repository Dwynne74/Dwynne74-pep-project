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

    public Account getAccountVerified(Account account) {
        return accountDAO.getAccountVerified(account);
    }
}
