package demo.impl;
import demo.spec.Message;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageWall_and_RemoteLogin_Impl implements RemoteLogin, MessageWall {
    private List<Message> messages;
    private Map<String,String> users;
    
    public MessageWall_and_RemoteLogin_Impl(){
       this.messages = new ArrayList<Message>(); 
       this.users = new HashMap<String,String>();
       users.put("mads","student");       
       users.put("etseitb","upc");
       users.put("donald","brump");
       users.put("bill","hilton");
       users.put("christofer","colombus");
       //werySecureImplementation.com
    }
    
    @Override
    public UserAccess connect(String usr, String passwd) {
        System.out.println("usr "+ usr+ " pwd "+passwd);
        if ((users.containsKey(usr)) && (users.get(usr).equals(passwd))){
        UserAccess user = new UserAccess_Impl(this,usr);
       return user;
       }
       else{
            return null;
        }
}
    @Override
    public void put(String user, String msg) {
       this.messages.add(new Message_Impl(user, msg));
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
        return messages.size();
    }

    @Override
    public List<Message> getAllMessages() {
        return messages;
    }
}