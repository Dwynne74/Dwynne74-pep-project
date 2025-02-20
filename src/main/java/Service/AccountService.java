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
        if(accountDAO.getAccountByUsername(account.getUsername()) == null || account.getUsername().length() > 0 
        || account.getPassword().length() > 3){
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }
    }

    public Message getMessageByUser(String username) {
        return accountDAO.getMessageByUser(username);
    }
}
