package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Workspace;
import spd.trello.repository.CRUDRepository;

@Service
public class WorkspaceService extends AbstractService<Workspace> {

    public WorkspaceService(CRUDRepository<Workspace> workspaceRepository) {
        super(workspaceRepository);
    }


}
