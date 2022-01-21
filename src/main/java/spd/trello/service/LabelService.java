/*package spd.trello.service;

import spd.trello.domain.Label;
import spd.trello.repository.CRUDRepository;

import java.util.List;
import java.util.UUID;

public class LabelService extends AbstractService<Label>{

    public LabelService(CRUDRepository<Label> labelRepository){
        super(labelRepository);
    }

    public Label create(String name){
        Label label = new Label();
        label.setId(UUID.randomUUID());
        label.setName(name);
        repository.create(label);
        return repository.findById(label.getId());
    }


    public Label update(Label label) {
        return repository.update(label);
    }

    public List<Label> getAll() {
        return repository.getAll();
    }

    public Label findById(UUID id) {
        return repository.findById(id);
    }

    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}*/
