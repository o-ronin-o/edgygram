package Backend;

import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class UserDatabase extends Database<User> {
    public UserDatabase() {
        super("users.json");
    }

    @Override
    public boolean add(User user) {
    ArrayList<User> users=getAll();
    for(User userr:users){
        if(userr.getUsername().equals(user.getUsername())){
            JOptionPane.showMessageDialog(null,"Username already exists","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if(userr.getEmail().equals(user.getEmail())){
            JOptionPane.showMessageDialog(null,"Email already exists","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(userr.getId().equals(user.getId())){
            JOptionPane.showMessageDialog(null,"Id already exists","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    users.add(user);
    save(users);
    return true;
    }

    @Override
    public void remove(User user) {
    ArrayList<User> users=getAll();
        for(User userr:users){
            if(userr.getId().equals(user.getId())){
                users.remove(userr);
                save(users);
                return;
            }
        }
    }

    @Override
    public ArrayList<User> getAll(){
        return load(new TypeToken<ArrayList<User>>() {}.getType());
    }

    @Override
    public User getById(String id) {
        ArrayList<User> users=getAll();
        for(User user:users){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
}
