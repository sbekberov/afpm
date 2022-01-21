package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.Member;
import spd.trello.domain.User;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepository;
import spd.trello.service.WorkspaceService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static spd.trello.Helper.getNewMember;
import static spd.trello.Helper.getNewUser;

public class WorkspaceTest extends BaseTest {
    public WorkspaceTest(){
        service = new WorkspaceService(new WorkspaceRepository(dataSource));
    }
    private final WorkspaceService service;

    @Test
    public void successCreate() {
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace testWorkspace = service.create(member, "test", "test");
        assertNotNull(testWorkspace);
        assertAll(
                () -> assertEquals("test@gmail", testWorkspace.getCreatedBy()),
                () -> assertNull(testWorkspace.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testWorkspace.getCreatedDate()),
                () -> assertNull(testWorkspace.getUpdatedDate()),
                () -> assertEquals("test", testWorkspace.getName()),
                () -> assertEquals("test", testWorkspace.getDescription())
        );
    }
    @Test
    public void findAll(){
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace testFirstWorkspace = service.create(member, "firstWorkspace", "firstDescription");
        Workspace testSecondWorkspace = service.create(member, "secondWorkspace", "secondDescription");
        assertNotNull(testFirstWorkspace);
        assertNotNull(testSecondWorkspace);
        assertNotNull(testFirstWorkspace);
        assertNotNull(testSecondWorkspace);
        List<Workspace> testWorkspace = service.getAll();
        assertAll(
                () -> assertTrue(testWorkspace.contains(testFirstWorkspace)),
                () -> assertTrue(testWorkspace.contains(testSecondWorkspace))
        );
    }
   @Test
    public void createFailure(){
       User user = getNewUser("test@gmail");
       Member member = getNewMember(user);
       IllegalStateException ex = assertThrows(
               IllegalStateException.class,
               () -> service.create(member, null, "Description"),
               "expected to throw  IllegalStateException, but it didn't"
      );
      assertEquals("Error WorkspaceRepository create", ex.getMessage());
   }
    @Test
    public void findById(){
        UUID id = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                ()->service.findById(id),
                "no exception"
        );
        assertEquals("Workspace with ID: " + id + " doesn't exists", ex.getMessage());
    }
    @Test
    public void delete(){
        User user = getNewUser("test@gmail");
        Member member = getNewMember(user);
        Workspace testWorkspace = service.create(member, "test", "test");
        assertNotNull(testWorkspace);
        UUID id = testWorkspace.getId();
        assertAll(
                ()->assertTrue(service.delete(id)),
                ()->assertFalse(service.delete(id))
        );
    }
   @Test
    public void update() {
        User users = getNewUser("test@gmail");
        Member member = getNewMember(users);
        Workspace workspace = service.create(member, "test", "test");
        assertNotNull(workspace);
        workspace.setName("Workspace");
        workspace.setDescription("Description");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        Workspace testWorkspace = service.update(member, workspace);
        assertAll(
                () -> assertEquals("test@gmail", testWorkspace.getCreatedBy()),
                () -> assertEquals("test@gmail", testWorkspace.getUpdatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testWorkspace.getCreatedDate()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testWorkspace.getUpdatedDate()),
                () -> assertEquals("Workspace", testWorkspace.getName()),
                () -> assertEquals("Description", testWorkspace.getDescription()),
                () -> assertEquals(WorkspaceVisibility.PRIVATE, testWorkspace.getVisibility())
        );
    }
}