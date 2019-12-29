package si.fri.rso.lib;

public class UserChannelData {
    private String userId;
    private Integer channelId;


    public Integer getChannelId() {
        return channelId;
    }
    public String getUserId() {
        return userId;
    }
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
