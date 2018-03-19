package lnu.asd.messenger.repository;

import lnu.asd.messenger.domain.dbentity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GroupRepository extends JpaRepository<Group, Long> {
}
