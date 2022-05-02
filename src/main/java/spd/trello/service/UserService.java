package spd.trello.service;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spd.trello.domain.User;
import spd.trello.repository.UserRepository;
import spd.trello.validators.UserValidator;

import java.util.UUID;

@Service
public class UserService extends AbstractService<User, UserRepository, UserValidator> {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    public UserService(UserRepository repository,PasswordEncoder passwordEncoder,UserValidator userValidator,MemberService memberService) {
        super(repository,userValidator);
        this.passwordEncoder = passwordEncoder;
        this.memberService = memberService;
    }
    public User register(User user)  {
        encodePassword(user);
        User result =super.create(user);
        return result;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public void delete(UUID id) {
        memberService.deleteMemberForUser(id);
        super.delete(id);
    }

}
