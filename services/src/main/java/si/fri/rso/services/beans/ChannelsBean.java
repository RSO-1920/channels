package si.fri.rso.services.beans;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import io.netty.channel.ChannelConfig;
import si.fri.rso.config.ChannelConfigProperties;
import si.fri.rso.lib.ChannelDTO;
import si.fri.rso.lib.ChannelData;
import si.fri.rso.lib.FileStorageBucketCreation;
import si.fri.rso.lib.UserChannelData;
import si.fri.rso.lib.responses.ResponseDTO;
import si.fri.rso.services.models.converters.ChannelConverter;
import si.fri.rso.services.models.converters.UserOnChannelConverter;
import si.fri.rso.services.models.entities.ChannelEntity;
import si.fri.rso.services.models.entities.ChannelTypeEntity;
import si.fri.rso.services.models.entities.UsersOnChannelEntity;
import si.fri.rso.services.utils.DbUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class ChannelsBean {

    @Inject
    private EntityManager em;

    @Inject
    private DbUtils dbUtils;

    @Inject
    ChannelConfigProperties channelConfigProperties;

    @Inject
    @DiscoverService(value = "rso1920-fileStorage")
    private Optional<String> fileStorageUrl;

    private Client httpClient;

    @PostConstruct
    private void init() {
        this.httpClient = ClientBuilder.newClient();
    }

    public ChannelDTO getChannel(Integer channelId) {
        ChannelEntity c = em.find(ChannelEntity.class, channelId);

        if (c == null) {
            return null;
        }

        return ChannelConverter.toDTO(c);
    }

    public List<ChannelDTO> getChannels() {
        Query q = em.createNamedQuery("getChannels");
        List<ChannelEntity> channels = (List<ChannelEntity>) q.getResultList();

        return channels.stream().map(ChannelConverter::toDTO).collect(Collectors.toList());
    }

    public List<ChannelDTO> getUserChannels(String userId) {
        Query q = em.createNamedQuery("getChannelsForUser").setParameter(1, userId);
        List<ChannelEntity> channels = (List<ChannelEntity>) q.getResultList();

        return channels.stream().map(ChannelConverter::toDTO).collect(Collectors.toList());
    }

    public List<String> getChannelUsers(Integer channelId) {
        Query q = em.createNamedQuery("getUsersForChannel").setParameter(1, channelId);
        List<UsersOnChannelEntity> usersOnChannel = (List<UsersOnChannelEntity>) q.getResultList();

        List<String> usersIds = new ArrayList<String>();

        for (UsersOnChannelEntity usersOnChannelEntity : usersOnChannel) {
            usersIds.add(usersOnChannelEntity.getUserId());
        }

        // TODO get all users based on id.. call user service :)

        return usersIds;
    }

    public boolean addUserOnChannel(UserChannelData userChannelData) {
        ChannelEntity c = em.find(ChannelEntity.class, userChannelData.getChannelId());
        if (c == null) {
            System.out.println("channel not found");
            return false;
        }

        if (c.getChannelTypeEntity().getTypeId() == 1) {
            System.out.println("channel reserved only for owner");
            return false;
        }

        UsersOnChannelEntity usersOnChannelEntity = UserOnChannelConverter.toEntity(userChannelData, c);
        usersOnChannelEntity = (UsersOnChannelEntity) dbUtils.createNewEntity(usersOnChannelEntity);

        if (usersOnChannelEntity == null || usersOnChannelEntity.getId() == null) {
            System.out.println("user on channel entity not created");
            return false;
        }

        return true;
    }


    public ResponseDTO createChannel(ChannelData newChannel, String requestId){
        System.out.println("request id METHOD: " + requestId);

        if (!this.fileStorageUrl.isPresent()) {
            return null;
        }
        try{
            System.out.println("File-storage-url: " +  this.fileStorageUrl.get() + this.channelConfigProperties.getFileStorageCreateBucketURI()+ "/" + newChannel.getChannelName().replace(" ", "_").trim());

            Response success = this.httpClient
                    .target(this.fileStorageUrl.get() + this.channelConfigProperties.getFileStorageCreateBucketURI()+ "/" + newChannel.getChannelName().replace(" ", "-").trim())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("uniqueRequestId", requestId)
                    .post( null);

            System.out.println("Bucket creation status: " + success.getStatus());
            if (success.getStatus() == 200) {
                System.out.println("User bucket creation success");
                System.out.println(success.getEntity());
                Gson gson = new Gson();
                FileStorageBucketCreation  bucketData = gson.fromJson(success.readEntity(String.class), FileStorageBucketCreation.class);
                System.out.println("CREATED CHANNEL: " + bucketData.getName());

                newChannel.setBucketName(bucketData.getName());

                Query q = em.createNamedQuery("getTypeOnId").setParameter(1, newChannel.getChannelType());
                ChannelTypeEntity channelTypeEntity = (ChannelTypeEntity) q.getSingleResult();

                ChannelEntity channelEntity = ChannelConverter.toEntity(newChannel, channelTypeEntity);
                channelEntity = (ChannelEntity) dbUtils.createNewEntity(channelEntity);

                if (channelEntity == null || channelEntity.getChannelId() == null) {
                    System.out.println("Channel not created");
                    return null;
                }

                UsersOnChannelEntity usersOnChannelEntity = new UsersOnChannelEntity(newChannel.getAdminId(), channelEntity);
                usersOnChannelEntity = (UsersOnChannelEntity) dbUtils.createNewEntity(usersOnChannelEntity);

                if (usersOnChannelEntity == null || usersOnChannelEntity.getId() == null) {
                    System.out.println("user on channel not created");
                    return null;
                }

                System.out.println("Channel created");

                return new ResponseDTO(200, "bucket creation success", ChannelConverter.toDTO(channelEntity));

            } else {
                return new ResponseDTO(400, "bucket file stoarage creation failed", null);
            }
        }catch (WebApplicationException | ProcessingException e) {
            e.printStackTrace();
            return new ResponseDTO(200, "api for creating bucket not reachabel", null);
        }
    }

    public ChannelDTO renameChannel(ChannelData updateChannelData){
        ChannelEntity c = em.find(ChannelEntity.class, updateChannelData.getChannelId());
        if (c == null) {
            return null;
        }

        ChannelEntity updatedChannelEntity = ChannelConverter.toEntityUpdate(updateChannelData, c);

        try {
            dbUtils.beginTx();
            updatedChannelEntity = em.merge(updatedChannelEntity);
            dbUtils.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            dbUtils.rollbackTx();
        }

        return ChannelConverter.toDTO(updatedChannelEntity);
    }

    public boolean removeUserOnChannel(String userId, String channelId) {
        ChannelEntity c = em.find(ChannelEntity.class, channelId);
        if (c == null) {
            System.out.println("channel with channel id doesn't exist");
            return false;
        }

        if (c.getChannelTypeEntity().getTypeId() == 1) {
            System.out.println("can not delete owner from user default channel");
            return false;
        }

        Query q = em.createNamedQuery("getUserChannelEntity").setParameter(1, userId).setParameter(2, channelId);
        UsersOnChannelEntity usersOnChannelEntity;
        try {
            usersOnChannelEntity = (UsersOnChannelEntity) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("No record in DB");
            return false;
        }

        try {
            dbUtils.beginTx();
            em.remove(usersOnChannelEntity);
            dbUtils.commitTx();
        } catch (Exception e) {
            e.printStackTrace();
            dbUtils.rollbackTx();
            return false;
        }

        return true;
    }

    public int deleteChannel(Integer channelId, boolean canDeleteDefaultChannel) {
        ChannelEntity c = em.find(ChannelEntity.class, channelId);
        if (c == null || ( c.getChannelTypeEntity().getTypeId() == 1 && !canDeleteDefaultChannel)) {
            System.out.println("Channel not found or not aloud to be deleted");
            return 0;
        }

        Query query = em.createNamedQuery("deleteUsersOnChannel").setParameter(1, channelId);
        int numDeletedUsers = 0;
        try {
            dbUtils.beginTx();
             numDeletedUsers = query.executeUpdate();
            em.remove(c);
            dbUtils.commitTx();
            System.out.println("deleted users from channel: " + numDeletedUsers);
        } catch (Exception e) {
            e.printStackTrace();
            dbUtils.rollbackTx();
            return 0;
        }

        return numDeletedUsers;
    }

}
