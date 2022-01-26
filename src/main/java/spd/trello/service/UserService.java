package spd.trello.service;


import org.springframework.stereotype.Service;
import spd.trello.domain.User;
import spd.trello.repository.CRUDRepository;

@Service
public class UserService extends AbstractService<User> {

    public UserService(CRUDRepository<User> userRepository) {
        super(userRepository);
    }


}
