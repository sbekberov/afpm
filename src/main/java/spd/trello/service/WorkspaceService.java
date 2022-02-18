package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Workspace;
import spd.trello.repository.WorkspaceRepository;

@Service
public class WorkspaceService extends AbstractService<Workspace, WorkspaceRepository> {
    @Autowired
    public WorkspaceService(WorkspaceRepository repository) {
        super(repository);
    }
}
