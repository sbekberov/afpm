package spd.trello.service;

import spd.trello.domain.Label;
import spd.trello.repository.LabelRepository;

import java.util.UUID;

public class LabelService {

    LabelRepository labelRepository = new LabelRepository();

    public Label create(String name) throws IllegalAccessException {
        Label label = new Label();
        label.setId(UUID.randomUUID());
        label.setName(name);
        labelRepository.create(label);
        return labelRepository.findById(label.getId());
    }


    public void update(Label label) {
        labelRepository.update(label);
    }

    public void getAll() {
        labelRepository.getAll();
    }

    public Label findById(UUID id) {
        Label label = null;
        try {
            label = labelRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return label;
    }

    public boolean delete(UUID id) {
        return labelRepository.delete(id);
    }
}
