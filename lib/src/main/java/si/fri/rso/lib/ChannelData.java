package si.fri.rso.lib;

public class ChannelData {
    private Integer channelId;
    private String adminId;
    private String channelName;
    private Integer channelType;

    private String bucketName;

    public String getChannelName() {
        return channelName;
    }
    public String getAdminId() {
        return adminId;
    }
    public Integer getChannelId() {
        return channelId;
    }
    public Integer getChannelType() {
        return channelType;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
