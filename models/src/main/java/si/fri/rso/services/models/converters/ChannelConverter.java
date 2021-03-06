package si.fri.rso.services.models.converters;

import si.fri.rso.lib.ChannelDTO;
import si.fri.rso.lib.ChannelData;
import si.fri.rso.lib.ChannelTypeDTO;
import si.fri.rso.services.models.entities.ChannelEntity;
import si.fri.rso.services.models.entities.ChannelTypeEntity;

import java.util.Optional;

public class ChannelConverter {
    public static ChannelDTO toDTO (ChannelEntity channelEntity) {
        ChannelDTO channel = new ChannelDTO();

        ChannelTypeDTO channelTypeDTO  = new ChannelTypeDTO();
        channelTypeDTO.setTypeId(channelEntity.getChannelTypeEntity().getTypeId());
        channelTypeDTO.setTypeName(channelEntity.getChannelTypeEntity().getTypeName());

        channel.setAdminId(channelEntity.getAdminId());
        channel.setChannelId(channelEntity.getChannelId());
        channel.setChannelName(channelEntity.getChannelName());
        channel.setChannelType(channelTypeDTO);
        channel.setBucketName(channelEntity.getBucketName());

        return channel;
    }

    public static ChannelEntity toEntity (ChannelData channelData, ChannelTypeEntity channelTypeEntity) {
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setAdminId(channelData.getAdminId());
        channelEntity.setChannelName(channelData.getChannelName());
        channelEntity.setChannelTypeEntity(channelTypeEntity);
        // System.out.println("channel name: "+ channelData.getChannelName().replace(" ", "_").trim());
        channelEntity.setBucketName(channelData.getBucketName());

        return channelEntity;
    }

    public static ChannelEntity toEntityUpdate (ChannelData channelData, ChannelEntity entity) {
        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setAdminId(entity.getAdminId());
        channelEntity.setChannelTypeEntity(entity.getChannelTypeEntity());
        channelEntity.setChannelId(entity.getChannelId());

        channelEntity.setChannelName(channelData.getChannelName());

        return channelEntity;
    }
}
