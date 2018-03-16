package lnu.asd.messanger.repository;

import lnu.asd.messanger.domain.dbentity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserName(String userName);
}
