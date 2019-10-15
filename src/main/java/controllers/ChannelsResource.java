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

    /*
    @GET
    @Path("count")
    public Response getCommentsCount(@QueryParam("imageId") Integer imageId) {

        List<Comment> comments;

        if (imageId != null) {
            comments = commentsBean.getCommentsForImage(imageId);
        } else {
            comments = commentsBean.getComments();
        }

        return Response.ok(comments.size()).build();
    }
    */

}