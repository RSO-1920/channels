package si.fri.rso.services.models.entities;


import si.fri.rso.services.models.interfaces.MainEntity;

import javax.persistence.*;

@Entity
@Table(name = "channel_type")

@NamedNativeQueries({
        @NamedNativeQuery(name = "getTypes",
                query = "SELECT * FROM channelType",
                resultClass = ChannelTypeEntity.class),
        @NamedNativeQuery(name = "getTypeOnId",
                query = "SELECT * FROM channel_type WHERE channel_type.type_id = ?1",
                resultClass = ChannelTypeEntity.class),
})

public class ChannelTypeEntity implements MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "type_name")
    private String typeName;

    public ChannelTypeEntity(){}

    public ChannelTypeEntity(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }
    public String getTypeName() {
        return typeName;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
