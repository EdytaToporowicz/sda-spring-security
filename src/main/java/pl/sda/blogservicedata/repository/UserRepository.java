package pl.sda.blogservicedata.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.blogservicedata.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(final String email);
}
