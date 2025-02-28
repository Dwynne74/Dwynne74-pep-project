package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessage() {
        return messageDAO.getMessage();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message createNewMessage(Message message) {
        if(messageDAO.postedByExist(message.getPosted_by()) == true && message.getMessage_text().length() > 0
        && message.getMessage_text().length() <= 255) {
            return messageDAO.createNewMessage(message);
        } else {
            return null;
        }
    }

    public List<Message> getMessageByUser(int account_id) {
        return messageDAO.getMessageByUser(account_id);
    }

    public Message deleteMessageById(int messageId) {
        Message deletedMessage = messageDAO.getMessageById(messageId);
        if(deletedMessage != null){
            messageDAO.deleteMessageById(messageId);
            return deletedMessage;
        } else {
            return null;
        }
    }

}
