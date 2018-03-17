package lnu.asd.messenger.domain.dbentity;

import javax.persistence.*;

@Entity
@Table(name = "groups")

public class Group {
    @Id
    @Column(name = "group_id", columnDefinition = "serial")
    @SequenceGenerator(name="group_id_seq", sequenceName="group_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="group_id_seq")
    private Long id;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
