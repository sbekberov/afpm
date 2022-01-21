package spd.trello.service;

import spd.trello.domain.Member;
import spd.trello.domain.Workspace;
import spd.trello.repository.CRUDRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class WorkspaceService extends AbstractService<Workspace> {

   public WorkspaceService(CRUDRepository<Workspace> workspaceRepository){
       super(workspaceRepository);
   }


   public Workspace create(Member member,String name, String description) {
        Workspace workspace = new Workspace();
        workspace.setId(UUID.randomUUID());
        workspace.setName(name);
        workspace.setDescription(description);
        workspace.setCreatedBy(member.getCreatedBy());
        workspace.setCreatedDate(Date.valueOf(LocalDate.now()));
        repository.create(workspace);
        return repository.findById(workspace.getId());
    }


    public Workspace update(Member member, Workspace workspace){
       workspace.setUpdatedDate(Date.valueOf(LocalDate.now()));
       workspace.setUpdatedBy(member.getCreatedBy());
       return repository.update(workspace);
    }


    public List<Workspace> getAll() {
        return repository.getAll();
    }


    public Workspace findById(UUID id) {
        return repository.findById(id);
    }


    public boolean delete(UUID id) {
        return repository.delete(id);
    }


}
