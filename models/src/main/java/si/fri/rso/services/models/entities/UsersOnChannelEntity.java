package si.fri.rso.services.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "users_on_channel")

@NamedNativeQueries({
        @NamedNativeQuery(name = "getAllUsersOnChannels",
                query = "SELECT * FROM users_on_channel",
                resultClass = UsersOnChannelEntity.class),
})

public class UsersOnChannelEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "fk_channel_id")
    private ChannelEntity channelEntity;

    public Integer getUserId() {
        return userId;
    }

    public ChannelEntity getChannelEntity() {
        return channelEntity;
    }

    public void setChannelEntity(ChannelEntity channelEntity) {
        this.channelEntity = channelEntity;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
