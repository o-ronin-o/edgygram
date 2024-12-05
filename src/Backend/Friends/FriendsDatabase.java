package Backend.Friends;

import Backend.Database;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendsDatabase extends Database<HashMap<String,FriendData>> {
    public FriendsDatabase() {
        super("Friends.json");
    }

    @Override
    public boolean add(HashMap<String, FriendData> item) {
        return false;
    }

    @Override
    public void remove(HashMap<String, FriendData> item) {

    }

    @Override
    public ArrayList<HashMap<String, FriendData>> getAll() {
        return null;
    }
    public HashMap<String, FriendData> loadHashMap(Type type) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            File file = new File(fileName);
            if (!file.exists()) {
                return new HashMap<>();
            }
            HashMap<String, FriendData> map = gson.fromJson(reader, type);
            return map == null ? new HashMap<>() : map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    public HashMap<String, FriendData> loadFriendData() {
        return loadHashMap(new TypeToken<HashMap<String, FriendData>>() {}.getType());
    }
    public void saveAll(HashMap<String, FriendData> data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            gson.toJson(data, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
