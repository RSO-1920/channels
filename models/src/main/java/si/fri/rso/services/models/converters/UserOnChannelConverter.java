package si.fri.rso.services.models.converters;

import si.fri.rso.lib.UserChannelData;
import si.fri.rso.services.models.entities.ChannelEntity;
import si.fri.rso.services.models.entities.UsersOnChannelEntity;

public class UserOnChannelConverter {

    public static UsersOnChannelEntity toEntity(UserChannelData userChannelData, ChannelEntity channelEntity) {
        UsersOnChannelEntity usersOnChannelEntity = new UsersOnChannelEntity();
        usersOnChannelEntity.setUserId(userChannelData.getUserId());
        usersOnChannelEntity.setChannelEntity(channelEntity);

        return usersOnChannelEntity;
    }
}
