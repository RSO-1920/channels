package si.fri.rso.lib;

public class ChannelTypeDTO {
    private Number typeId;
    private String typeName;

    public Number getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setTypeId(Number typeId) {
        this.typeId = typeId;
    }
}
