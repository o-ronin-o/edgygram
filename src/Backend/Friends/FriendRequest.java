package Backend.Friends;

public class FriendRequest {
    private String SenderId;
    private String ReceiverId;

    public FriendRequest(String SenderId, String ReceiverId) {
        this.SenderId = SenderId;
        this.ReceiverId = ReceiverId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }
}
