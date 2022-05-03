package spd.trello.validators;

import org.springframework.stereotype.Component;
import spd.trello.domain.CardTemplate;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.CardTemplateRepository;

@Component
public class CardTemplateValidator extends AbstractValidator<CardTemplate> {

    private final CardTemplateRepository cardTemplateRepository;
    private final HelperValidator<CardTemplate> helper;

    public CardTemplateValidator(CardTemplateRepository cardTemplateRepository, HelperValidator<CardTemplate> helper) {
        this.cardTemplateRepository = cardTemplateRepository;
        this.helper = helper;
    }

    private void validateCardTemplateFields(StringBuilder exceptions, CardTemplate entity) {
        if (entity.getName().length() < 2 || entity.getName().length() > 30) {
            exceptions.append("The name field must be between 2 and 30 characters long. \n");
        }
        if (entity.getDescription() != null && (entity.getDescription().length() < 2 || entity.getDescription().length() > 255)) {
            exceptions.append("The description field must be between 2 and 255 characters long. \n");
        }
    }

    @Override
    public void validateSaveEntity(CardTemplate entity) {
        StringBuilder exceptions = helper.validateCreateEntity(entity);
        validateCardTemplateFields(exceptions, entity);
        helper.throwException(exceptions);
    }

    @Override
    public void validateUpdateEntity(CardTemplate entity) {
        var oldCardTemplate = cardTemplateRepository.findById(entity.getId());
        if (oldCardTemplate.isEmpty()) {
            throw new ResourceNotFoundException("Can not update non-existent CardTemplate!");
        }
        StringBuilder exceptions = helper.validateEntityUpdate(oldCardTemplate.get(), entity);
        validateCardTemplateFields(exceptions, entity);
        helper.throwException(exceptions);
    }
}
