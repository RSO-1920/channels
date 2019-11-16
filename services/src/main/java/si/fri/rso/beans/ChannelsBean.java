package si.fri.rso.beans;

import si.fri.rso.lib.Channel;
import si.fri.rso.lib.ChannelDTO;

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

        channels.add(new Channel("Name of First channel", 0, 1));
        channels.add(new Channel("Name of Second channel", 1, 1));
        channels.add(new Channel("RSO_CHANNEL", 2, 1));

    }


    public List<Channel> getChannels() {

        return channels;

    }
    public Channel add_Channel(ChannelDTO newChannel){

        Channel created_channel = new Channel(newChannel.getChannelName(), channels.size() , newChannel.getChannelAdminId());
        this.channels.add(created_channel);
        return created_channel;
    }

    public Channel rename_Channel(ChannelDTO newChannel){
        for (Channel channel : channels) {
            if (channel.getChannel_id().equals(newChannel.getChannelId())) {
                channel.setChannel_name(newChannel.getChannelName());
                return channel;
            }
        }
        return null;
    }
    public int print(String in){
        System.out.println(in);
        return -1;
    }

}
