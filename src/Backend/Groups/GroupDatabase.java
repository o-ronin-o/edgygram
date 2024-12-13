package Backend.Groups;

import Backend.Database;
import Backend.Post;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupDatabase extends Database<HashMap<String, Group>> {

    public GroupDatabase() {
        super("Groups.json"); // Initialize with the file name for storing group data
    }

    @Override
    public boolean add(HashMap<String, Group> item) {
        return false; // Adding an entire HashMap is not supported directly
    }

    @Override
    public void remove(HashMap<String, Group> item) {

    }

    @Override
    public ArrayList<HashMap<String, Group>> getAll() {
        return null; // This method can be implemented to return all groups if necessary
    }

    @Override
    public HashMap<String, Group> getById(String id) {
        return null; // This method can be implemented to get a group by its ID
    }

    // Load the HashMap containing groups from the JSON file
    public HashMap<String, Group> loadHashMap(Type type) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            File file = new File(fileName);
            if (!file.exists()) {
                return new HashMap<>(); // Return an empty map if the file doesn't exist
            }
            HashMap<String, Group> map = gson.fromJson(reader, type);
            return map == null ? new HashMap<>() : map; // Handle null case by returning an empty map
        } catch (IOException e) {
            e.printStackTrace(); // Print error stack trace
            return new HashMap<>(); // Return an empty map in case of an error
        }
    }


    public HashMap<String, Group> loadGroupData() {
        return loadHashMap(new TypeToken<HashMap<String, Group>>() {}.getType());
    }

    // Save all group data to the JSON file
    public void saveAll(HashMap<String, Group> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            System.out.println("Saving the following data: " + data);  // Debugging: Print the data being saved
            gson.toJson(data, writer);
            writer.flush(); // Ensure all data is written to the file
        } catch (IOException e) {
            throw new RuntimeException("Failed to save groups data.", e); // Throw an error if saving fails
        }
    }
}
