package controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import controllers.Channel;
import controllers.ChannelsBean;
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
    public Response addChannel(Channel newChannel) {

        int a = 3;  // ChannelsBean.add_Channel(newChannel);


        return Response.status(Response.Status.OK).entity(a).build();
    }
}