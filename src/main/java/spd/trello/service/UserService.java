package spd.trello.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spd.trello.domain.User;
import spd.trello.repository.UserRepository;

@Service
public class UserService extends AbstractService<User, UserRepository> {

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }
    public User register(User user)  {

        encodePassword(user);
        return repository.save(user);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean checkIfUserExist(String email) {
        return repository.findByEmail(email) != null;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
