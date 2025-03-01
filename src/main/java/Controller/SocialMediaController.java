package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
    // Use these to determine what to do with the methods
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::createAccountHandler);
        app.post("/login", this::verifyAccountLoginHandler);
        app.post("/messages", this::createNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByUserHandler);

        return app;
    }

    /**
     * Create a new account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     */
    private void createAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        if(newAccount != null) {
            context.status(200);
            context.json(newAccount);
        } else {
            context.status(400);
        }
    }

    /**
     * Verify the login of the account.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     */
    private void verifyAccountLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account verifyAccount = accountService.getAccountVerified(account);
        if(verifyAccount != null){
            context.status(200);
            context.json(verifyAccount);
        } else {
            context.status(401);
        }
    }

    /**
     * Create a new message.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     */
    private void createNewMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.createNewMessage(message);
        if(newMessage != null) {
            context.status(200);
            context.json(newMessage);
        } else {
            context.status(400);
        }
    }

    /**
     * Retrieve all messages that exists in the database.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }
    
    /**
     * Retrieve the messages using the message_id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     */
    private void getMessageByIdHandler(Context context) throws JsonProcessingException {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        context.status(200);
        if(message != null){
            context.json(message);
        } else {
            context.json("");
        }
    }
    /**
     * Delete a message using the message_id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);
        context.status(200);
        if(deletedMessage != null) {
            context.json(deletedMessage);
        } else {
            context.json("");
        }
    }

    /**
     * Update the message(if exists) using the message_id from a user.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     */
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message messageToUpdate = mapper.readValue(context.body(), Message.class);
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageById(messageToUpdate, messageId);
        if(updatedMessage != null){
            context.status(200);
            context.json(updatedMessage);
        } else {
            context.status(400);
        }
    }

    /**
     * Retrieve messages by a user using account_id.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByUserHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id")); // account_id == posted_by
        List<Message> message = messageService.getMessageByUser(accountId);
        context.status(200);
        if(message != null){
            context.json(message);
        } else {
            context.json("");
        }
    }


}