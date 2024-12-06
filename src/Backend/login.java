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

    public String updateStatus(int numb){
        if (numb ==1)
            return "online";
        return "offline";
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
