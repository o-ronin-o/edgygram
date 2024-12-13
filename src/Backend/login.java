package Backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class login {


    Gson gson=new GsonBuilder().setPrettyPrinting().create();

    public void updateStatus(User user,int numb){
        if (numb ==1)
          user.setStatus("online");
        else{
            user.setStatus("offline");
        }
        Database<User> userDatabase=  UserDatabase.getInstance();
        ArrayList<User> users= userDatabase.getAll();
        for (User userr : users) {
            if (userr.getId().equals(user.getId())) {
                userr.setStatus(user.getStatus());
                break;
            }
        }
        userDatabase.save(users);
    }
    public boolean checkEmailandPassword(String useremail,String password,String name){
        FileReader f= null;

        try {
            f = new FileReader("users.json");
            Type loginlists=new TypeToken<ArrayList<User>>(){}.getType();
            List<User> list= new ArrayList<>(gson.fromJson(f,loginlists));
            for(int i=0;i<list.size();i++){
                if( password.equals(list.get(i).getPassword())&&(name.equals(list.get(i).getUsername())|| useremail.equals(list.get(i).getEmail()))) {
                    list.get(i).setStatus("Online");
                    return true;
                }
            }
            f.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public User getUser(String useremail){
        FileReader f= null;
        try{
            f = new FileReader("users.json");
            Type loginlists=new TypeToken<ArrayList<User>>(){}.getType();
            List<User> list= new ArrayList<>(gson.fromJson(f,loginlists));
            for(int i=0;i<list.size();i++){
                if(  useremail.equals(list.get(i).getEmail())||useremail.equals(list.get(i).getUsername())) {
                    list.get(i).setStatus("Online");
                    User user=list.get(i);
                    System.out.println("username: "+ user.getUsername());
                    return user;
                }
            }
            f.close();

        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
