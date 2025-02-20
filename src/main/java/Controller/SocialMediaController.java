package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 /**
  * Endpounts:
  * Create a new account: POST localhost:8080/register. 
  * The body will contain a representation of a JSON Account, but will not contain an account_id.
  *
  * Verify login: POST localhost:8080/login.
  * The request body will contain a JSON representation of an Account, not containing an account_id.
  *
  * Create new Message: POST localhost:8080/messages.
  * The request body will contain a JSON representation of a message, 
  * which should be persisted to the database, but will not contain a message_id.
  *
  * Retrieve all new messages: GET localhost:8080/messages.
  * The response body should contain a JSON representation of a list containing all messages retrieved from the database.
  *
  * Retrieve message by ID: GET localhost:8080/messages/{message_id}.
  * The response body should contain a JSON representation of the message identified by the message_id
  *
  * Delete message by ID: DELETE localhost:8080/messages/{message_id}.
  * The deletion of an existing message should remove an existing message from the database.
  *
  * Update a message text by ID: PATCH localhost:8080/messages/{message_id}.
  * The request body should contain a new message_text values to replace the message identified by message_id. 
  * The request body can not be guaranteed to contain any other information.
  *
  * Retrieve message by user: GET localhost:8080/accounts/{account_id}/messages.
  * The response body should contain a JSON representation of a list containing all messages posted by a particular user, 
  * which is retrieved from the database.
  */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
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
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    private void createAccountHandler(Context context) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(context.body(), Account.class);
            Account newAccount = accountService.addAccount(account);
            if(newAccount != null) {
                context.json(newAccount);
                context.status(200);
            } else {
                context.status(400);
            }
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void verifyAccountLoginHandler(Context context) {
        // context.json("sample text");
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createNewMessageHandler(Context context) {
        // context.json("sample text");
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }
    
    /**
     * This is an example handler for an example endpoint.
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
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageByIdHandler(Context context) {
        // context.json("sample text");
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMessageByIdHandler(Context context) {
        // context.json("sample text");
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByUserHandler(Context context) {
        String username = context.pathParam("username");
        Message message = accountService.getMessageByUser(username);
        context.status(200);
        if(message != null){
            context.json(message);
        } else {
            context.json("");
        }
    }


}