package si.fri.rso.api.v1.controllers;

import com.google.gson.Gson;
import si.fri.rso.api.v1.MainController;
import si.fri.rso.lib.UserChannelData;
import si.fri.rso.services.beans.ChannelsBean;
import si.fri.rso.lib.ChannelDTO;
import si.fri.rso.lib.ChannelData;

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
public class ChannelsController extends MainController {

    @Inject
    private ChannelsBean channelsBean;

    @GET
    public Response getChannels() {

        List<ChannelDTO> channels = channelsBean.getChannels();

        return Response.status(200).entity(this.responseOk("", channels)).build();
    }

    @GET
    @Path("userChannels/{userId}")
    public Response getUserChannels(@PathParam("userId") Integer userId) {
        List<ChannelDTO> userChannels = channelsBean.getUserChannels(userId);

        return Response.status(200).entity(this.responseOk("", userChannels)).build();
    }


    @GET
    @Path("channelUsers/{channelId}")
    public Response getChannelUsers(@PathParam("channelId") Integer channelId) {
        List<Integer> usersIds =  channelsBean.getChannelUsers(channelId);

        return Response.status(200).entity(this.responseOk("", usersIds)).build();
    }


    @POST
    @Path("createChannel")
    public Response createChannel(String body) {

        Gson gson = new Gson();
        ChannelData  channelData = gson.fromJson(body, ChannelData.class);

        if (channelData.getChannelType() == null || channelData.getAdminId() == null || channelData.getChannelName() == null) {
            return Response.status(400).entity(this.responseError(400, "channelType, adminId or channelName is missing")).build();
        }
        ChannelDTO createdChannel = channelsBean.createChannel(channelData);

        if (createdChannel == null) {
            return Response.status(409).entity(this.responseError(409, "channel creation failed")).build();
        }

        return Response.status(200).entity(this.responseOk("", createdChannel)).build();
    }

    @PUT
    @Path("renameChannel")
    public Response renameChannel(String body) {
        Gson gson = new Gson();
        ChannelData  channelData = gson.fromJson(body, ChannelData.class);

        if (channelData.getChannelName() == null || channelData.getChannelId() == null) {
            return Response.status(400).entity(this.responseError(400, "channelName or channelId is missing")).build();
        }

        System.out.println("renaming channel: " + channelData.getChannelId() + " newName: " + channelData.getChannelName());

        ChannelDTO updatedChannelData = channelsBean.renameChannel(channelData);

        System.out.println(updatedChannelData.getChannelName());

        return Response.status(200).entity(this.responseOk("", updatedChannelData)).build();
    }

    @POST
    @Path("addUserOnChannel")
    public Response addUserOnChannel(String body) {
        Gson gson = new Gson();
        UserChannelData userChannelData = (UserChannelData)gson.fromJson(body, UserChannelData.class);
        if (userChannelData.getChannelId() == null || userChannelData.getUserId() == null) {
            return Response.status(400).entity(this.responseError(400, "channel id or userId is missing")).build();
        }

        boolean isCreated = channelsBean.addUserOnChannel(userChannelData);

        if (!isCreated) {
            return Response.status(409).entity(this.responseError(409, "user " + userChannelData.getUserId() +  " on channel: " + userChannelData.getChannelId() + " not added")).build();
        }

        return Response.status(200).entity(this.responseOk("user added succesfully", isCreated)).build();
    }

    @DELETE
    @Path("remove/user/{userId}/channel/{channelId}")
    public Response removeUserOnChannel(@PathParam("userId") Integer userId, @PathParam("channelId") Integer channelId) {

        boolean isDeleted = channelsBean.removeUserOnChannel(userId, channelId);

        if (!isDeleted) {
            return Response.status(409).entity(this.responseError(409, "user " +  userId +  " on channel: " + channelId + " not removed")).build();
        }

        return Response.status(200).entity(this.responseOk("user succesfully removed", isDeleted)).build();
    }

}