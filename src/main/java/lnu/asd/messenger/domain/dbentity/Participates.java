package lnu.asd.messenger.domain.dbentity;

import javax.persistence.*;

@Entity
@Table(name = "participates")

public class Participates {

    @EmbeddedId
    private ParticipatesID id;

    public ParticipatesID getId() {
        return id;
    }

    public void setId(ParticipatesID id) {
        this.id = id;
    }

}
