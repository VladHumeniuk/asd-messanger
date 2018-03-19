package lnu.asd.messenger.domain.dbentity;

import javax.persistence.*;

@Entity
@Table(name = "participates")

public class Participates {

    @EmbeddedId
    private ParticipatesID id;

    private Boolean isCreator;

    public ParticipatesID getId() {
        return id;
    }

    public void setId(ParticipatesID id) {
        this.id = id;
    }

    public Boolean getCreator() {
        return isCreator;
    }

    public void setCreator(Boolean creator) {
        isCreator = creator;
    }
}
