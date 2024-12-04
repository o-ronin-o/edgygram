package Backend;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class TestContentDatabase {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static String generateID() {
        return "CID-" + counter.incrementAndGet();
    }
    public static void main(String[] args) {
        // Create a ContentDatabase instance
        ContentDatabase contentDatabase = new ContentDatabase();

        // Add a Post to the ContentDatabase
        Post post = new Post(generateID(),"meow", "This is a post", LocalDateTime.now(), "Text");
        System.out.println("Adding Post...");
        contentDatabase.add(post);
        post = new Post(generateID(),"meow", "This is a post", LocalDateTime.now(), "Text");
        System.out.println("Adding Post...");
        contentDatabase.add(post);

        // Add a Story to the ContentDatabase
        Story story = new Story(generateID(),"Author2", "This is a story", LocalDateTime.now(), "Image");
        System.out.println("Adding Story...");
        contentDatabase.add(story);
        story = new Story(generateID(),"Author2", "This is a story", LocalDateTime.now(), "Image");
        System.out.println("Adding Story...");
        contentDatabase.add(story);

        // Display all Content
        System.out.println("\nAll Content:");
        contentDatabase.getAll().forEach(content -> System.out.println(content.getContent()));

        // Display all Posts
        System.out.println("\nAll Posts:");
        contentDatabase.getAllPosts().forEach(postItem -> System.out.println(postItem.getContent()));

        // Display all Stories
        System.out.println("\nAll Stories:");
        contentDatabase.getAllStories().forEach(storyItem -> System.out.println(storyItem.getContent()));

        // Remove a Post
        System.out.println("\nRemoving Post...");
        contentDatabase.remove(post);

        // Remove a Story
        System.out.println("\nRemoving Story...");
        contentDatabase.remove(story);

        // Display all Content after removal
        System.out.println("\nAll Content after removal:");
        contentDatabase.getAll().forEach(content -> System.out.println(content.getContent()));
        return;
    }

}
