package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Member;
import spd.trello.domain.Workspace;
import spd.trello.domain.enums.WorkspaceVisibility;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WorkspaceIntegrationTest extends AbstractIntegrationTest<Workspace> {
    private final String URL_TEMPLATE = "/workspaces";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        Member member = helper.getNewMember("s.bekberov@gmail.com");
        Workspace workspace = new Workspace();
        workspace.setCreatedBy(member.getCreatedBy());
        Set<UUID> membersId = new HashSet<>();
        membersId.add(member.getId());
        workspace.setMembersIds(membersId);
        workspace.setName("Test Workspace");
        MvcResult firstMvcResult = super.create(URL_TEMPLATE, workspace);
        Set<UUID> testFirstMembersId = helper.getIdsFromJson(getValue(firstMvcResult, "$.membersIds").toString());


        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(firstMvcResult, "$.id")),
                () -> assertEquals(workspace.getCreatedBy(), getValue(firstMvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(firstMvcResult, "$.createdDate")),
                () -> assertNull(getValue(firstMvcResult, "$.updatedBy")),
                () -> assertNull(getValue(firstMvcResult, "$.updatedDate")),
                () -> assertEquals(workspace.getName(), getValue(firstMvcResult, "$.name")),
                () -> assertNull(getValue(firstMvcResult, "$.description")),
                () -> assertEquals(WorkspaceVisibility.PUBLIC.toString(), getValue(firstMvcResult, "$.visibility")),
                () -> assertTrue(testFirstMembersId.contains(member.getId())),
                () -> assertEquals(1, testFirstMembersId.size())


        );
    }

    @Test
    public void createFailure() throws Exception {
        Workspace entity = new Workspace();
        MvcResult mvcResult = super.create(URL_TEMPLATE, entity);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        Workspace firstWorkspace = helper.getNewWorkspace("s.bekberov@gmail.com");
        Workspace secondWorkspace = helper.getNewWorkspace("bekberov@gmail.com");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<Workspace> testWorkspaces = helper.getWorkspacesArray(mvcResult);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertTrue(testWorkspaces.contains(firstWorkspace)),
                () -> assertTrue(testWorkspaces.contains(secondWorkspace))
        );
    }

    @Test
    public void findById() throws Exception {
        Workspace workspace = helper.getNewWorkspace("s.bekberov@gmail.com");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, workspace.getId());
        Set<UUID> testMembersId = helper.getIdsFromJson(getValue(mvcResult, "$.membersIds").toString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(workspace.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(workspace.getName(), getValue(mvcResult, "$.name")),
                () -> assertNull(getValue(mvcResult, "$.description")),
                () -> assertEquals(WorkspaceVisibility.PUBLIC.toString(), getValue(mvcResult, "$.visibility")),
                () -> assertTrue(testMembersId.contains(workspace.getMembersIds().iterator().next())),
                () -> assertEquals(1, testMembersId.size())
        );
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        Workspace workspace = helper.getNewWorkspace("s.bekberov@gmail.com");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, workspace.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<Workspace> testWorkspaces = helper.getWorkspacesArray(deleteMvcResult);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertFalse(testWorkspaces.contains(workspace))
        );
    }

    @Test
    public void deleteByIdFailure() throws Exception {
        MvcResult mvcResult = super.delete(URL_TEMPLATE, UUID.randomUUID());

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus())
        );
    }

    @Test
    public void update() throws Exception {
        Workspace workspace = helper.getNewWorkspace("s.bekberov@gmail.com");
        Member secondMember = helper.getNewMember("bekberov@gmail.com");
        workspace.setUpdatedBy(workspace.getCreatedBy());
        workspace.setName("Updated Workspace");
        workspace.setDescription("Updated description");
        workspace.setVisibility(WorkspaceVisibility.PUBLIC);
        Set<UUID> membersIds = workspace.getMembersIds();
        membersIds.add(secondMember.getId());
        workspace.setMembersIds(membersIds);
        MvcResult mvcResult = super.update(URL_TEMPLATE, workspace.getId(), workspace);
        Set<UUID> testMembersIds = helper.getIdsFromJson(getValue(mvcResult, "$.membersIds").toString());
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(workspace.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
                () -> assertEquals(workspace.getUpdatedBy(), getValue(mvcResult, "$.updatedBy")),
                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(workspace.getName(), getValue(mvcResult, "$.name")),
                () -> assertEquals(workspace.getDescription(), getValue(mvcResult, "$.description")),
                () -> assertEquals(WorkspaceVisibility.PUBLIC.toString(), getValue(mvcResult, "$.visibility")),
                () -> assertTrue(testMembersIds.contains(workspace.getMembersIds().iterator().next())),
                () -> assertTrue(testMembersIds.contains(workspace.getMembersIds().iterator().next())),
                () -> assertEquals(2, membersIds.size())
        );
    }

    @Test
    public void updateFailure() throws Exception {
        Workspace firstWorkspace = helper.getNewWorkspace("s.bekberov@gmail.com");
        firstWorkspace.setName(null);
        firstWorkspace.setUpdatedBy(firstWorkspace.getCreatedBy());

        Workspace secondWorkspace = new Workspace();
        secondWorkspace.setId(firstWorkspace.getId());

        MvcResult firstMvcResult = super.update(URL_TEMPLATE, firstWorkspace.getId(), firstWorkspace);
        MvcResult secondMvcResult = super.update(URL_TEMPLATE, secondWorkspace.getId(), secondWorkspace);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), secondMvcResult.getResponse().getStatus())
        );
    }
}
