package spd.trello.repository;

import org.springframework.stereotype.Component;
import spd.trello.domain.User;

@Component
public interface UserRepository extends AbstractRepository<User> {
}
