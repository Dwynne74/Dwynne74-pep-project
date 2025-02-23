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

    public Message getMessageByUser(int posted_by) {
        return messageDAO.getMessageByUser(posted_by);
    }

}
