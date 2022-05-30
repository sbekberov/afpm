package spd.trello.security.extrafilter;

import org.springframework.stereotype.Component;
import spd.trello.configuration.UserContextHolder;
import spd.trello.domain.Card;
import spd.trello.domain.Comment;
import spd.trello.domain.Member;
import spd.trello.domain.User;
import spd.trello.domain.enums.Permission;
import spd.trello.exception.SecurityAccessException;
import spd.trello.repository.CardRepository;
import spd.trello.repository.CommentRepository;
import spd.trello.repository.MemberRepository;
import spd.trello.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Component
public class CommentChecker extends AbstractChecker<Comment, CommentRepository> {

    private final CardRepository cardRepository;

    public CommentChecker(UserRepository userRepository, MemberRepository memberRepository, CommentRepository entityRepository, CardRepository cardRepository) {
        super(userRepository, memberRepository, entityRepository);
        this.cardRepository = cardRepository;
    }

    @Override
    protected void checkPostRequest(HttpServletRequest request, User user) {
        Comment comment = readFromJson(request, Comment.class);
        findMemberBy(comment, user);
    }

    @Override
    protected void checkMembership(UUID entityId, User user, Permission permission) {
        Comment comment = getComment(entityId);
        Member member = findMemberBy(comment, user);
        if (!user.getEmail().equals(comment.getCreatedBy()) ||
                !member.getRole().getPermissions().contains(Permission.WRITE))
            throw new SecurityAccessException("Member does not have enough access rights");
    }

    @Override
    protected Member findMemberBy(Comment entity, User user) {
        Card card = cardRepository.findById(entity.getCardId()).orElseThrow(EntityNotFoundException::new);
        List<Member> members = UserContextHolder.getMembersContext(user.getEmail());
        return members.stream()
                .filter(member -> card.getMembersIds().contains(member.getId()))
                .findFirst()
                .orElseThrow(() -> new SecurityAccessException("User: " + user.getEmail() + " does not have access to write comment"));
    }

    private Comment getComment(UUID id) {
        return entityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    protected void checkEntityAccessRights(UUID entityId, User user) {
        findMemberBy(getComment(entityId), user);
    }
}
