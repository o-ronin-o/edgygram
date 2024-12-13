package Backend;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Content {
    private String contentId;
    private String groupId;
    private String authorId;
    private String content;
    private String timeStamp;
    private String picPath;
    private String authorName;
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Content(String contentId, String authorId, String content, LocalDateTime timeStamp,String picPath) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.content = content;
        this.timeStamp = timeStamp.format(DateTimeFormatter.ISO_DATE_TIME);
        this.picPath = picPath;
    }

    public Content(String contentId, String authorId, String content, LocalDateTime timeStamp,String picPath,String groupId,String authorName) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.content = content;
        this.timeStamp = timeStamp.format(DateTimeFormatter.ISO_DATE_TIME);
        this.picPath = picPath;
        this.groupId = groupId;
        this.authorName = authorName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getPicPath() {
        return picPath;
    }
    public void setPicPath(String picPath){
        this.picPath = picPath;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getContentId() {
        return this.contentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public String getTimeStamp() {
        return timeStamp;
    }


    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp.format(formatter);
    }

    public String getPostString(String timeStamp,String content ){
        return " Posted at"+ timeStamp+"\n"+ content;
    }
    public String getStoryString(String content ){
        return  content;
    }

}
