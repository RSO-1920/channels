package controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ChannelsBean {

    private List<Channel> channels;

    @PostConstruct
    private void init() {

        channels = new ArrayList<>();

        channels.add(new Channel("Name of First channel", 1, 1));
        channels.add(new Channel("Name of Second channel", 2, 1));
        channels.add(new Channel("RSO_CHANNEL", 3, 1));

    }


    public List<Channel> getChannels() {

        return channels;

    }


}
