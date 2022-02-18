package spd.trello.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.User;
import spd.trello.repository.UserRepository;

@Service
public class UserService extends AbstractService<User, UserRepository> {
    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }
}
