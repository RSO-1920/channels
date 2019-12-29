package si.fri.rso.lib;

import java.util.Optional;

public class ChannelDTO {
    private Integer channelId;
    private String adminId;
    private String channelName;
    private Optional<ChannelTypeDTO> channelType;
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
    public ChannelTypeDTO getChannelType() {
        return channelType.orElse(null);
    }


    public void setChannelType(ChannelTypeDTO channelType) {
        this.channelType = Optional.ofNullable(channelType);
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

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
