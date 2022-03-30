package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Workspace;
import spd.trello.exception.BadRequestException;
import spd.trello.exception.ResourceNotFoundException;
import spd.trello.repository.WorkspaceRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class WorkspaceService extends AbstractService<Workspace, WorkspaceRepository> {
    @Autowired
    public WorkspaceService(WorkspaceRepository repository) {
        super(repository);
    }

    @Override
    public Workspace update(Workspace entity) {
        Workspace oldWorkspace = findById(entity.getId());
        entity.setUpdatedDate(Date.valueOf(LocalDate.now()));
        entity.setCreatedBy(oldWorkspace.getCreatedBy());
        entity.setCreatedDate(oldWorkspace.getCreatedDate());

        if (entity.getUpdatedBy() == null) {
            throw new BadRequestException("Not found updated by!");
        }

        if (entity.getName() == null && entity.getDescription() == null
                && entity.getVisibility().equals(oldWorkspace.getVisibility())
                && entity.getMembersIds().equals(oldWorkspace.getMembersIds())) {
            throw new ResourceNotFoundException();
        }

        if (entity.getName() == null) {
            entity.setName(oldWorkspace.getName());
        }
        if (entity.getDescription() == null && oldWorkspace.getDescription() != null) {
            entity.setDescription(oldWorkspace.getDescription());
        }

        try {
            return repository.save(entity);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
