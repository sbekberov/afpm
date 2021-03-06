package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Comment;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.CommentRepository;
import spd.trello.validators.CommentValidator;


import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class CommentService extends AbstractService<Comment, CommentRepository, CommentValidator> {
    @Autowired
    public CommentService(CommentRepository repository,CommentValidator commentValidator) {
        super(repository,commentValidator);
    }

    @Override
    public Comment update(Comment entity) {
        Comment oldCard = findById(entity.getId());
        entity.setCreatedDate(oldCard.getCreatedDate());
        entity.setUpdatedDate(LocalDateTime.now().withNano(0));
        entity.setCardId(oldCard.getCardId());

        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getText() == null) {
            throw new ResourceNotFoundException();
        }

        try {
            return repository.save(entity);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void deleteCommentsForCard(UUID cardId) {
        repository.findAllByCardId(cardId).forEach(comment -> delete(comment.getId()));
    }
}
