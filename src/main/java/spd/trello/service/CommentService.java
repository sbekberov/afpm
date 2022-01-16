package spd.trello.service;

import spd.trello.domain.Comment;
import spd.trello.domain.Member;
import spd.trello.repository.CommentRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class CommentService extends AbstractService<Comment> {

    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentService() {
        super();
        commentRepository = new   CommentRepository(dataSource);
    }


    public Comment create(Member member,String content,UUID cardId, UUID userId) throws IllegalAccessException {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setContent(content);
        comment.setCreatedBy("test");
        comment.setCreatedDate(Date.valueOf(LocalDate.now()));
        comment.setCardId(cardId);
        comment.setUsersId(userId);
        commentRepository.create(comment);
        return commentRepository.findById(comment.getId());
    }

    public void update(Comment comment) {
        commentRepository.update(comment);
    }


    public void getAll() {
        commentRepository.getAll();
    }

    public Comment findById(UUID id) {
        Comment comment = null;
        try {
            comment = commentRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return comment;
    }

    public boolean delete(UUID id) {
        return commentRepository.delete(id);
    }
}
