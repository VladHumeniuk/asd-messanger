package lnu.asd.messenger.web.entity.groupslist.response;

import lnu.asd.messenger.web.utils.EntityTypes;

public class Data {

    private Long id;

    private String type = EntityTypes.GROUP;

    private Attributes attributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}
