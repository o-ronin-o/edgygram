package Backend;

import java.time.LocalDateTime;

public class Story extends Content{

    private String ContentType;

    public Story(String contentId ,String authorId, String content, LocalDateTime timeStamp, String contentType) {
        super(contentId ,authorId, content, timeStamp);
        this.ContentType = "Story";
    }
    public String getContentType() {
        return ContentType;
    }
}
