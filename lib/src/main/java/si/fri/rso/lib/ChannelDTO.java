package si.fri.rso.lib;


public class ChannelDTO {

    private String channelName;
    private Integer channelAdminId;
    private Integer channelId;

    public String getChannelName() {
        return this.channelName;
    }
    public Integer getChannelAdminId() {
        return this.channelAdminId;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelAdminId(Integer channelAdminId) {
        this.channelAdminId = channelAdminId;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public void setChannelId(Integer Id) {
        this.channelId = Id;
    }
}