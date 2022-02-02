package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domain.Workspace;
import spd.trello.service.WorkspaceService;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController extends AbstractController<Workspace, WorkspaceService> {
    public WorkspaceController(WorkspaceService service) {
        super(service);
    }
}

