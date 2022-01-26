package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Comment;
import spd.trello.repository.CRUDRepository;

@Service
public class CommentService extends AbstractService<Comment> {

    public CommentService(CRUDRepository<Comment> commentRepository) {
        super(commentRepository);
    }
}
