package si.fri.rso.lib;

public class ChannelData {
    private Integer channelId;
    private Integer adminId;
    private String channelName;
    private Integer channelType;

    private String bucketName;

    public String getChannelName() {
        return channelName;
    }
    public Integer getAdminId() {
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

    public void setAdminId(Integer adminId) {
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
