package spd.trello.validators;

import org.springframework.stereotype.Component;
import spd.trello.domain.BoardTemplate;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.BoardTemplateRepository;

@Component
public class BoardTemplateValidator extends AbstractValidator<BoardTemplate> {

    private final BoardTemplateRepository boardTemplateRepository;
    private final HelperValidator<BoardTemplate> helper;

    public BoardTemplateValidator(BoardTemplateRepository boardTemplateRepository, HelperValidator<BoardTemplate> helper) {
        this.boardTemplateRepository = boardTemplateRepository;
        this.helper = helper;
    }

    private void validateBoardTemplateFields(StringBuilder exceptions, BoardTemplate entity) {
        if (entity.getName().length() < 2 || entity.getName().length() > 30) {
            exceptions.append("The name field must be between 2 and 30 characters long. \n");
        }
        if (entity.getDescription() != null && (entity.getDescription().length() < 2 || entity.getDescription().length() > 255)) {
            exceptions.append("The description field must be between 2 and 255 characters long. \n");
        }
    }

    @Override
    public void validateSaveEntity(BoardTemplate entity) {
        StringBuilder exceptions = helper.validateCreateEntity(entity);
        validateBoardTemplateFields(exceptions, entity);
        helper.throwException(exceptions);
    }

    @Override
    public void validateUpdateEntity(BoardTemplate entity) {
        var oldBoardTemplate = boardTemplateRepository.findById(entity.getId());
        if (oldBoardTemplate.isEmpty()) {
            throw new ResourceNotFoundException("Can not update non-existent BoardTemplate!");
        }
        StringBuilder exceptions = helper.validateEntityUpdate(oldBoardTemplate.get(), entity);
        validateBoardTemplateFields(exceptions, entity);
        helper.throwException(exceptions);
    }
}
