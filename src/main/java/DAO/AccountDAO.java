package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username, password) VALUES(?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                int generatedAccountId = rs.getInt(1);
                Account newAccount = new Account(generatedAccountId, account.getUsername(), account.getPassword());
                return newAccount;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean usernameAlreadyExist(String userName){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Message getMessageByUser(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT message.message_id, message.posted_by, message.message_text, message.time_posted_epoch "
            + "FROM message JOIN account ON message.posted_by = account.account_id "
            + "WHERE account.username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    
}
