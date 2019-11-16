package si.fri.rso.services.models.entities;

import si.fri.rso.services.models.interfaces.MainEntity;

import javax.persistence.*;

@Entity
@Table(name = "channel")

@NamedNativeQueries({
        @NamedNativeQuery(name = "getChannels",
                query = "SELECT * FROM channel",
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
    private Integer adminId;

    @ManyToOne
    @JoinColumn(name = "fk_type_id")
    private ChannelTypeEntity channelTypeEntity;

    public Integer getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public ChannelTypeEntity getChannelTypeEntity() {
        return channelTypeEntity;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
    public void setChannelTypeEntity(ChannelTypeEntity channelTypeEntity) {
        this.channelTypeEntity = channelTypeEntity;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
}
