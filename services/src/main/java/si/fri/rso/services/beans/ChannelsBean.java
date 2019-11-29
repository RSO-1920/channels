package si.fri.rso.services.beans;

import si.fri.rso.lib.ChannelDTO;
import si.fri.rso.lib.ChannelData;
import si.fri.rso.lib.UserChannelData;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class ChannelsBean {

    @Inject
    private EntityManager em;

    @Inject
    private DbUtils dbUtils;

    @PostConstruct
    private void init() {
    }

    public List<ChannelDTO> getChannels() {
        Query q = em.createNamedQuery("getChannels");
        List<ChannelEntity> channels = (List<ChannelEntity>) q.getResultList();

        return channels.stream().map(ChannelConverter::toDTO).collect(Collectors.toList());
    }

    public List<ChannelDTO> getUserChannels(Integer userId) {
        Query q = em.createNamedQuery("getChannelsForUser").setParameter(1, userId);
        List<ChannelEntity> channels = (List<ChannelEntity>) q.getResultList();

        return channels.stream().map(ChannelConverter::toDTO).collect(Collectors.toList());
    }

    public List<Integer> getChannelUsers(Integer channelId) {
        Query q = em.createNamedQuery("getUsersForChannel").setParameter(1, channelId);
        List<UsersOnChannelEntity> usersOnChannel = (List<UsersOnChannelEntity>) q.getResultList();

        List<Integer> usersIds = new ArrayList<Integer>();

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


    public ChannelDTO createChannel(ChannelData newChannel, String requestId){
        System.out.println("request id: " + requestId);

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
        // TODO call to create bucket

        return ChannelConverter.toDTO(channelEntity);
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

    public boolean removeUserOnChannel(Integer userId, Integer channelId) {
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
