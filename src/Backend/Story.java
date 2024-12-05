package Backend;

import java.time.LocalDateTime;

public class Story extends Content{

    private String ContentType;

    public Story(String contentId ,String authorId, String content, LocalDateTime timeStamp,String picPath, String contentType) {
        super(contentId ,authorId, content, timeStamp,picPath);
        this.ContentType = "Story";
    }
    public String getContentType() {
        return ContentType;
    }
}
