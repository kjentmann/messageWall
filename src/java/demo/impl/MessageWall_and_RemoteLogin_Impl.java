package demo.impl;
import demo.spec.Message;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.util.ArrayList;
import java.util.List;

public class MessageWall_and_RemoteLogin_Impl implements RemoteLogin, MessageWall {

    private List<Message> messages;
    
  
    public MessageWall_and_RemoteLogin_Impl(){
       this.messages = new ArrayList<Message>(); 
       this.put("empty", "none");
    }
    
    @Override
    public UserAccess connect(String usr, String passwd) {
           UserAccess user = new UserAccess_Impl(this,usr);
           if (passwd == "password"){
           return user;
           }
           else{
                return user;
                //throw new UnsupportedOperationException("Error, wrong passowrd"); 
            }
    }

    @Override
    public void put(String user, String msg) {
       this.messages.add(new Message_Impl(user, msg));
        //throw new UnsupportedOperationException("Numbers Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String user, int index) {
        if (messages.get(index) != null){
            messages.remove(index);
        }else{
            throw new UnsupportedOperationException("Index error while deleting message."); 
        }
        return true;
    }
            
    @Override
    public Message getLast() {
           if (messages.size()==0 ){
               throw new UnsupportedOperationException("No messages to get");
           }else{
       return messages.get(messages.size()-1);
    }
    }       

    @Override
    public int getNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Message> getAllMessages() {
        return messages;
    }
}
