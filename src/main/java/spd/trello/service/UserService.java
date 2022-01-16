package spd.trello.service;


import spd.trello.domain.User;
import spd.trello.repository.UserRepository;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public class UserService extends AbstractService<User>{

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {
        super();
        userRepository = new UserRepository(dataSource);
    }

    public User create(String firstName, String lastName, String email) throws IllegalAccessException {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setTimeZone(ZoneId.systemDefault().toString());
        userRepository.create(user);
        return userRepository.findById(user.getId());
    }


    public void update(User users) throws IllegalAccessException {
        userRepository.update(users);
    }
    public List<User> getAll() {
        userRepository.getAll();
        return null;
    }

    public User findById(UUID id) {
        User users = null;
        try {
            users = userRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean delete(UUID id) {
        return userRepository.delete(id);
    }
}
