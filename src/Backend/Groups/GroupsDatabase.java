package Backend.Groups;

import Backend.ContentHandling;
import Backend.Database;
import Backend.User;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.util.ArrayList;

public class GroupsDatabase extends Database<GroupData> {
    private String Groupsfilename = "groups.json";

    public GroupsDatabase() {
        super("groups.json");
    }

    @Override
    public boolean add(GroupData item) {
        ArrayList<GroupData> groups=getAll();
        for(GroupData group:groups){
            if(group.getGroupName().equals(item.getGroupName())){
                JOptionPane.showMessageDialog(null,"Group name already exists","Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if(group.getGroupId().equals(item.getGroupId())){
                JOptionPane.showMessageDialog(null,"Id already exists","Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        groups.add(item);
        save(groups);
        return true;
    }
    //used from ,not main one

    @Override
    public void remove(GroupData item) {
        ArrayList<GroupData> groups=getAll();
        for(GroupData group:groups){
            if(group.getGroupName().equals(item.getGroupId())){
                groups.remove(group);
                save(groups);
                return;
            }
        }
    }

    @Override
    public ArrayList<GroupData> getAll() {
        fileName = "groups.json";
        return load(new TypeToken<ArrayList<GroupData>>() {}.getType());
    }

    @Override
    public GroupData getById(String id) {
        ArrayList<GroupData> groups=getAll();
        for(GroupData group:groups){
            if(group.getGroupId().equals(id)){
                return group;
            }
        }
        return null;
    }  }

