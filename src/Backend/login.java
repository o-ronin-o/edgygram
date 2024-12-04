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
    private String userID;
    private String email;
    private String username;
    private String password;
    private String dateOfBirth;
    private String status;

    Gson gson=new GsonBuilder().setPrettyPrinting().create();

    public String updateStatus(int numb){
        if (numb ==1)
            return "online";
        return "offline";
    }
    public boolean checkEmailandPassword(String useremail,String password,String name){
        FileReader f= null;
        try {
            f = new FileReader("Users.json");
            Type loginlists=new TypeToken<ArrayList<login>>(){}.getType();
            List<login> list= new ArrayList<>(gson.fromJson(f,loginlists));
            for(int i=0;i<list.size();i++){
                if( password.equals(list.get(i).password)&&(name.equals(list.get(i).username)|| useremail.equals(list.get(i).email)))
                    return true;
            }
            f.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
