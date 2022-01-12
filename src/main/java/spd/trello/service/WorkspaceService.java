package spd.trello.service;

import spd.trello.domain.Member;
import spd.trello.domain.Workspace;
import spd.trello.repository.WorkspaceRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class WorkspaceService extends AbstractService<Workspace> {

    WorkspaceRepository workspaceRepository;


    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public WorkspaceService() {
        super();
        workspaceRepository = new WorkspaceRepository(dataSource);
    }


    public Workspace create(String name, String description, Member member) throws IllegalAccessException {
        Workspace workspace = new Workspace();
        workspace.setName("Test");
        workspace.setDescription("test");
        workspace.setId(UUID.randomUUID());
        workspace.setCreatedBy("test");
        workspace.setUpdatedBy("test");
        workspace.setCreatedDate(Date.valueOf(LocalDate.now()));
        workspaceRepository.create(workspace);
        return workspaceRepository.findById(workspace.getId());
    }


    public void update(Workspace workspace) throws IllegalAccessException {
        workspaceRepository.update(workspace);
    }


    public void getAll() {
        workspaceRepository.getAll();
    }


    public Workspace findById(UUID id) {
        Workspace workspace = null;
        try {
            workspace = workspaceRepository.findById(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return workspace;
    }


    public boolean delete(UUID id) {
        return workspaceRepository.delete(id);
    }


}
