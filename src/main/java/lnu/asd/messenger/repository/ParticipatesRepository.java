package lnu.asd.messenger.repository;

import lnu.asd.messenger.domain.dbentity.Group;
import lnu.asd.messenger.domain.dbentity.Participates;
import lnu.asd.messenger.domain.dbentity.ParticipatesID;
import lnu.asd.messenger.domain.dbentity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ParticipatesRepository extends JpaRepository<Participates, ParticipatesID> {

    List<Participates> findById_Group(Group group);

    List<Participates> findById_User(User user);

    Participates findById_GroupAndId_User(Group group, User user);
}
