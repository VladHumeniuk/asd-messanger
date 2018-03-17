package lnu.asd.messenger.repository;

import lnu.asd.messenger.domain.dbentity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserName(String userName);
}
