package si.fri.rso.services.models.entities;


import javax.persistence.*;

@Entity
@Table(name = "channel_type")

@NamedNativeQueries({
        @NamedNativeQuery(name = "getTypes",
                query = "SELECT * FROM channelType",
                resultClass = ChannelTypeEntity.class),
})

public class ChannelTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "type_name")
    private String typeName;

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
