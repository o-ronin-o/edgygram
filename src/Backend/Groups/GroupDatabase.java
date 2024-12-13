package Backend.Groups;

import Backend.Database;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupDatabase extends Database<HashMap<String, Group>> {

    public GroupDatabase() {
        super("Groups.json");
    }

    @Override
    public boolean add(HashMap<String, Group> item) {
        return false;
    }

    @Override
    public void remove(HashMap<String, Group> item) {
        // Implement remove logic if needed
    }

    @Override
    public ArrayList<HashMap<String, Group>> getAll() {
        return null;
    }

    @Override
    public HashMap<String, Group> getById(String id) {
        return null;
    }

    public HashMap<String, Group> loadHashMap(Type type) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            File file = new File(fileName);
            if (!file.exists()) {
                return new HashMap<>();
            }
            HashMap<String, Group> map = gson.fromJson(reader, type);
            return map == null ? new HashMap<>() : map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public HashMap<String, Group> loadGroupData() {
        return loadHashMap(new TypeToken<HashMap<String, Group>>() {}.getType());
    }

    public void saveAll(HashMap<String, Group> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            System.out.println("Saving the following data: " + data);  // Debugging line
            gson.toJson(data, writer); // Save the entire map
            writer.flush(); // Ensure all data is written
        } catch (IOException e) {
            throw new RuntimeException("Failed to save groups data.", e);
        }
    }
}
