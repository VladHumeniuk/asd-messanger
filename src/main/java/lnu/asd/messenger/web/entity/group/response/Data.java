package lnu.asd.messenger.web.entity.group.response;

import lnu.asd.messenger.web.utils.EntityTypes;

public class Data {

    private long id;

    private String type = EntityTypes.GROUP;

    private Attributes attributes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
