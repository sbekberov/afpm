package spd.trello.service;

import spd.trello.domain.Comment;
import spd.trello.domain.Member;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CommentService extends AbstractService<Comment> {

    public CommentService(CRUDRepository<Comment> commentRepository){
        super(commentRepository);
    }


    public Comment create(Member member,String content,UUID cardId, UUID userId) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setContent(content);
        comment.setCreatedBy(member.getCreatedBy());
        comment.setCreatedDate(Date.valueOf(LocalDate.now()));
        comment.setCardId(cardId);
        comment.setUsersId(userId);
        repository.create(comment);
        return repository.findById(comment.getId());
    }

    public Comment update(Member member ,Comment entity){
        Comment oldCard = repository.findById(entity.getId());
        if (!member.getCreatedBy().equals(oldCard.getCreatedBy())) {
            throw new IllegalStateException("This member cannot update comment!");
        }
        entity.setUpdatedBy(member.getCreatedBy());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        if (entity.getContent() == null) {
            entity.setContent(oldCard.getContent());
        }
        return repository.update(entity);
    }


    public List<Comment> getAll() {
        return repository.getAll();
    }

    public Comment findById(UUID id) {
        return repository.findById(id);
    }

    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
