package spd.trello.service;


import spd.trello.domain.User;
import spd.trello.repository.CRUDRepository;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public class UserService extends AbstractService<User>{

    public UserService(CRUDRepository<User> userRepository){
        super(userRepository);
    }

    public User create(String firstName, String lastName, String email) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setTimeZone(ZoneId.systemDefault().toString());
        repository.create(user);
        return repository.findById(user.getId());
    }


    public User update(User users){
       return repository.update(users);
    }

    public List<User> getAll() {
       return repository.getAll();
    }

    public User findById(UUID id) {
       return repository.findById(id);
    }

    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
