package si.fri.rso.services.models.entities;

import si.fri.rso.services.models.interfaces.MainEntity;

import javax.persistence.*;

@Entity
@Table(name = "channel")

@NamedNativeQueries({
        @NamedNativeQuery(name = "getChannels",
                query = "SELECT * FROM channel",
                resultClass = ChannelEntity.class),

        @NamedNativeQuery(name = "getOneChannel",
                query = "SELECT * FROM channel WHERE channel.channel_id = ?1",
                resultClass = ChannelEntity.class),

        @NamedNativeQuery(name = "getChannelsForUser",
                query = "SELECT * FROM channel INNER JOIN users_on_channel ON channel.channel_id = users_on_channel.fk_channel_id WHERE users_on_channel.user_id = ?1",
                resultClass = ChannelEntity.class),
})

public class ChannelEntity implements MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Integer channelId;

    @Column(name = "channel_name")
    private String channelName;

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "bucket_name")
    private String bucketName;

    @ManyToOne
    @JoinColumn(name = "fk_type_id")
    private ChannelTypeEntity channelTypeEntity;

    public Integer getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getAdminId() {
        return adminId;
    }

    public ChannelTypeEntity getChannelTypeEntity() {
        return channelTypeEntity;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public void setChannelTypeEntity(ChannelTypeEntity channelTypeEntity) {
        this.channelTypeEntity = channelTypeEntity;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
