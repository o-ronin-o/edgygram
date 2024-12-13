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
        super("Groups.json");
    }

    @Override
    public boolean add(HashMap<String, Group> item) {
        return false;
    }

    @Override
    public void remove(HashMap<String, Group> item) {
        if (item == null || item.isEmpty()) {
            System.out.println("No group to remove.");
            return;
        }

        try {
            // Load the current group data
            HashMap<String, Group> groups = loadGroupData();

            // Iterate through the map and remove the group(s)
            for (String groupId : item.keySet()) {
                if (groups.containsKey(groupId)) {
                    groups.remove(groupId); // Remove group from the map
                    System.out.println("Group with ID " + groupId + " removed.");
                }
            }

            // Save the updated data back to the file
            saveAll(groups);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to remove group.");
        }
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

    public ArrayList<HashMap<String, Group>> getAllPosts() {
        fileName = "groupPosts.json";
        return load(new TypeToken<ArrayList<Post>>() {
        }.getType());

    }
    public HashMap<String, ArrayList<Post>> loadPostsData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("groupPosts.json"))) {
            File file = new File("groupPosts.json");
            if (!file.exists()) {
                return new HashMap<>();
            }
            Type type = new TypeToken<HashMap<String, ArrayList<Post>>>() {}.getType();
            HashMap<String, ArrayList<Post>> postsMap = gson.fromJson(reader, type);
            return postsMap == null ? new HashMap<>() : postsMap;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    public void savePostsData(HashMap<String, ArrayList<Post>> postsData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("groupPosts.json"))) {
            gson.toJson(postsData, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save posts data.", e);
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
