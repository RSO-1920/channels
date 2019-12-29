package si.fri.rso.api.v1.controllers;

import com.google.gson.Gson;
import com.kumuluz.ee.logs.cdi.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.api.v1.MainController;
import si.fri.rso.lib.UserChannelData;
import si.fri.rso.lib.responses.ResponseDTO;
import si.fri.rso.services.beans.ChannelsBean;
import si.fri.rso.lib.ChannelDTO;
import si.fri.rso.lib.ChannelData;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Log
@ApplicationScoped
@Path("/channels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChannelsController extends MainController {

    @Inject
    private ChannelsBean channelsBean;

    @Inject
    HttpServletRequest requestheader;

    @GET
    @Operation(description = "Get all channels", summary = "get all channels", tags = "get, channel", responses = {
            @ApiResponse(responseCode = "200",
                    description = "get channel",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChannelDTO.class)))
            )
    })
    @Timed(name = "channels_time_all")
    @Counted(name = "channels_counted_all")
    @Metered(name = "channels_metered_all")
    public Response getChannels() {

        List<ChannelDTO> channels = channelsBean.getChannels();

        return Response.status(200).entity(this.responseOk("", channels)).build();
    }

    @GET
    @Operation(description = "Get ane channel", summary = "get one channel", tags = "get, channel", responses = {
            @ApiResponse(responseCode = "200",
                    description = "get channel",
                    content = @Content(schema = @Schema(implementation = ChannelDTO.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "error getting channel",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
    @Timed(name = "channels_time_one")
    @Counted(name = "channels_counted_one")
    @Metered(name = "channels_metered_one")
    @Path("{channelId}")
    public Response getChannel(@PathParam("channelId") Integer channelId) {

        if (channelId == null) {
            return Response.status(409).entity(this.responseError(400, "channelId not given")).build();
        }

        ChannelDTO channel = channelsBean.getChannel(channelId);
        if (channel == null) {
            return Response.status(400).entity(this.responseError(400, "channel with id not found")).build();
        }

        return Response.status(200).entity(this.responseOk("", channel)).build();
    }

    @GET
    @Operation(description = "Get users channels", summary = "Get users channels", tags = "get, channel, user", responses = {
            @ApiResponse(responseCode = "200",
                    description = "get users channels",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChannelDTO.class)))
            )
    })
    @Timed(name = "channels_time_userChannels")
    @Counted(name = "channels_counted_userChannels")
    @Metered(name = "channels_metered_userChannels")
    @Path("userChannels/{userId}")
    public Response getUserChannels(@PathParam("userId") Integer userId) {
        List<ChannelDTO> userChannels = channelsBean.getUserChannels(userId);

        return Response.status(200).entity(this.responseOk("", userChannels)).build();
    }


    @GET
    @Operation(description = "Get channel users", summary = "Get channel users", tags = "get, channel, user", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Get channel users",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class)))
            )
    })
    @Timed(name = "channels_time_channelsUsers")
    @Counted(name = "channels_counted_channelsUsers")
    @Metered(name = "channels_metered_channelsUsers")
    @Path("channelUsers/{channelId}")
    public Response getChannelUsers(@PathParam("channelId") Integer channelId) {
        List<String> usersIds =  channelsBean.getChannelUsers(channelId);

        return Response.status(200).entity(this.responseOk("", usersIds)).build();
    }


    @POST
    @Operation(description = "Create channel", summary = "Create channel", tags = "create, channel", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Create channel",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "missing params for channel creation",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "channel creation failed",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
    @Timed(name = "channels_time_createChannel")
    @Counted(name = "channels_counted_createChannel")
    @Metered(name = "channels_metered_createChannel")
    @Path("createChannel")
    public Response createChannel(String body) {

        Gson gson = new Gson();
        ChannelData  channelData = gson.fromJson(body, ChannelData.class);

        if (channelData.getChannelType() == null || channelData.getAdminId() == null || channelData.getChannelName() == null) {
            return Response.status(400).entity(this.responseError(400, "channelType, adminId or channelName is missing")).build();
        }

        String requestHeader = requestheader.getHeader("uniqueRequestId");

        ResponseDTO createdChannel = channelsBean.createChannel(channelData, requestHeader != null ? requestHeader : UUID.randomUUID().toString() );

        if (createdChannel == null) {
            return Response.status(409).entity(this.responseError(409, "channel creation failed")).build();
        }

        return Response.status(200).entity(createdChannel).build();
    }

    @PUT
    @Operation(description = "Update channel params", summary = "Update channel params", tags = "update, channel", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Channel update success",
                    content = @Content(schema = @Schema(implementation = ChannelDTO.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Missing update channel params",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
    @Timed(name = "channels_time_renameChannel")
    @Counted(name = "channels_counted_renameChannel")
    @Metered(name = "channels_metered_renameChannel")
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
    @Operation(description = "add user on channel", summary = "add user on channel", tags = "add, user,  channel", responses = {
            @ApiResponse(responseCode = "200",
                    description = "User successfully added on selected channel",
                    content = @Content(schema = @Schema(implementation = Boolean.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Missing user and channel data",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Error adding user on channel",
                    content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            )
    })
    @Timed(name = "channels_time_addUserOnChannel")
    @Counted(name = "channels_counted_addUserOnChannel")
    @Metered(name = "channels_metered_addUserOnChannel")
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
    @Operation(description = "Remove user on channel", summary = "Remove user on channel", tags = "remove, user ,channel", responses = {
            @ApiResponse(responseCode = "200",
                    description = "remove user on channel success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "409",
                    description = "User is not removed on channel",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDTO.class)))
            )
    })
    @Timed(name = "channels_time_removeUserOnChannel")
    @Counted(name = "channels_counted_removeUserOnChannel")
    @Metered(name = "channels_metered_removeUserOnChannel")
    @Path("remove/user/{userId}/channel/{channelId}")
    public Response removeUserOnChannel(@PathParam("userId") Integer userId, @PathParam("channelId") Integer channelId) {

        boolean isDeleted = channelsBean.removeUserOnChannel(userId, channelId);

        if (!isDeleted) {
            return Response.status(409).entity(this.responseError(409, "user " +  userId +  " on channel: " + channelId + " not removed")).build();
        }

        return Response.status(200).entity(this.responseOk("user successfully removed", isDeleted)).build();
    }

    @DELETE
    @Operation(description = "Delete channel", summary = "Delete channel", tags = "delete, channel", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Delete channel success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDTO.class)))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Channel with given id not deleted",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseDTO.class)))
            )
    })
    @Timed(name = "channels_time_removeChannel")
    @Counted(name = "channels_counted_removeChannel")
    @Metered(name = "channels_metered_removeChannel")
    @Path("{channelId}/channel")
    public Response deleteChannel(@PathParam("channelId") Integer channelId, @QueryParam("deleteDefault") boolean deleteDefault) {
        System.out.println("can delete default: " + deleteDefault);

        int numDeletedUsersOnChannel = channelsBean.deleteChannel(channelId, deleteDefault);
        if (numDeletedUsersOnChannel == 0) {
            return Response.status(409).entity(this.responseError(409, "Channel: " + channelId + " not deleted")).build();
        }

        return Response.status(200).entity(this.responseOk("Channel removed", "num dleted users: " + numDeletedUsersOnChannel)).build();
    }

}