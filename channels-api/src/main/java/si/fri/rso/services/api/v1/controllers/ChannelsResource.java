package si.fri.rso.services.api.v1.controllers;

import si.fri.rso.services.beans.ChannelsBean;
import si.fri.rso.services.lib.Channel;
import si.fri.rso.services.lib.ChannelDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/channels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChannelsResource {

    @Inject
    private ChannelsBean channelsBean;

    @GET
    public Response getChannels(/*@QueryParam("channelId") Integer channelId*/) {

        List<Channel> channels;

        channels = channelsBean.getChannels();

        return Response.ok(channels).build();
    }

    @POST
    @Path("addChannel")
    public Response addChannel(ChannelDTO newChannel) {

        System.out.println("Channel name: " + newChannel.getChannelName());
        System.out.println("Channel userId: " + newChannel.getChannelAdminId());

        boolean isCreated = true;
        channelsBean.add_Channel(newChannel);


        return Response.status(Response.Status.OK).entity(isCreated).build();
    }

    @POST
    @Path("renameChannel")
    public Response renameChannel(ChannelDTO newChannel) {


        boolean isCreated = true;
        channelsBean.rename_Channel(newChannel);


        return Response.status(Response.Status.OK).entity(isCreated).build();
    }

}