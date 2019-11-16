package si.fri.rso.lib;

public class ChannelDTO {
    private Integer channelId;
    private Integer adminId;
    private String channelName;
    private ChannelTypeDTO channelTypeDTO;

    public String getChannelName() {
        return channelName;
    }
    public Integer getAdminId() {
        return adminId;
    }
    public Integer getChannelId() {
        return channelId;
    }
    public ChannelTypeDTO getChannelTypeDTO() {
        return channelTypeDTO;
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
    public void setChannelTypeDTO(ChannelTypeDTO channelTypeDTO) {
        this.channelTypeDTO = channelTypeDTO;
    }
}
