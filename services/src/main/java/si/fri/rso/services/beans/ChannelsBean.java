package si.fri.rso.services.beans;

import si.fri.rso.lib.ChannelDTO;
import si.fri.rso.lib.ChannelData;
import si.fri.rso.services.models.converters.ChannelConverter;
import si.fri.rso.services.models.entities.ChannelEntity;
import si.fri.rso.services.models.entities.ChannelTypeEntity;
import si.fri.rso.services.models.entities.UsersOnChannelEntity;
import si.fri.rso.services.utils.DbUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    public ChannelDTO createChannel(ChannelData newChannel){
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

    public ChannelDTO rename_Channel(ChannelData updateChannelData){
        return null;
    }

}
