package si.fri.rso.services.models.entities;

import si.fri.rso.services.models.interfaces.MainEntity;

import javax.persistence.*;

@Entity
@Table(name = "users_on_channel",
        uniqueConstraints = @UniqueConstraint(columnNames={"user_id", "fk_channel_id"}))

@NamedNativeQueries({
        @NamedNativeQuery(name = "getAllUsersOnChannels",
                query = "SELECT * FROM users_on_channel",
                resultClass = UsersOnChannelEntity.class),
        @NamedNativeQuery(name = "getUserChannelEntity",
                query = "SELECT * FROM users_on_channel where users_on_channel.user_id = ?1 and users_on_channel.fk_channel_id = ?2",
                resultClass = UsersOnChannelEntity.class),
        @NamedNativeQuery(name = "getUsersForChannel",
                query = "SELECT * FROM users_on_channel INNER JOIN channel ON users_on_channel.fk_channel_id = channel.channel_id  WHERE users_on_channel.fk_channel_id = ?1",
                resultClass = UsersOnChannelEntity.class),
        @NamedNativeQuery(name = "deleteUsersOnChannel",
                query = "DELETE FROM users_on_channel WHERE users_on_channel.fk_channel_id = ?1"),
})

public class UsersOnChannelEntity implements MainEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "fk_channel_id")
    private ChannelEntity channelEntity;

    public UsersOnChannelEntity(){}
    public UsersOnChannelEntity(Integer userId, ChannelEntity channelEntity){
        this.userId = userId;
        this.channelEntity = channelEntity;
    }


    public Integer getUserId() {
        return userId;
    }

    public ChannelEntity getChannelEntity() {
        return channelEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setChannelEntity(ChannelEntity channelEntity) {
        this.channelEntity = channelEntity;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
