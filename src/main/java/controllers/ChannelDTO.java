package controllers;

import java.util.ArrayList;
import java.util.List;


public class ChannelDTO {

    private String channelName;
    private Integer channelAdminId;

    public String getChannelName() {
        return this.channelName;
    }
    public Integer getChannelAdminId() {
        return this.channelAdminId;
    }

    public void setChannelUserId(Integer channelAdminId) {
        this.channelAdminId = channelAdminId;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}