package spd.trello.validators;

import org.springframework.stereotype.Component;
import spd.trello.domain.Label;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.CardRepository;
import spd.trello.repository.LabelRepository;

@Component
public class LabelValidator extends AbstractValidator<Label> {
    private final CardRepository cardRepository;
    private final LabelRepository labelRepository;
    private final ColorValidator colorValidator;

    public LabelValidator(CardRepository cardRepository, LabelRepository labelRepository,
                          ColorValidator colorValidator) {
        this.cardRepository = cardRepository;
        this.labelRepository = labelRepository;
        this.colorValidator = colorValidator;
    }

    private void validateLabelFields(StringBuilder exceptions, Label entity) {
        if (entity.getName() == null){
            throw new BadRequestException("Name cannot be null!");
        }
        if (entity.getColor() == null){
            throw new ResourceNotFoundException("Not found color!");
        }
        if (entity.getName().length() < 2 || entity.getName().length() > 30){
            exceptions.append("The name field must be between 2 and 30 characters long.");
        }
    }

    @Override
    public void validateSaveEntity(Label entity) {
        StringBuilder exceptions = new StringBuilder();

        if (entity.getCardId() == null || !cardRepository.existsById(entity.getCardId())) {
            throw new BadRequestException("The cardId field must belong to a card.");
        }
        validateLabelFields(exceptions, entity);
        colorValidator.validateSaveEntity(entity.getColor());
        throwException(exceptions);
    }

    @Override
    public void validateUpdateEntity(Label entity) {
        var oldLabel = labelRepository.findById(entity.getId());
        if (oldLabel.isEmpty()) {
            throw new BadRequestException("Cannot update non-existent label!");
        }
        if(!oldLabel.get().getCardId().equals(entity.getCardId())){
            throw new BadRequestException("Label cannot be transferred to another card.");
        }
        StringBuilder exceptions = new StringBuilder();
        validateLabelFields(exceptions, entity);
        colorValidator.validateUpdateEntity(entity.getColor());
        throwException(exceptions);
    }

    private void throwException(StringBuilder exceptions) {
        if (exceptions.length() != 0) {
            throw new BadRequestException(exceptions.toString());
        }
    }
}
