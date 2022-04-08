package spd.trello.integrationalTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Member;
import spd.trello.domain.User;
import spd.trello.domain.enums.Role;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberIntegrationTest extends AbstractIntegrationTest<Member> {
    private final String URL_TEMPLATE = "/members";

    @Autowired
    private Helper helper;

    @Test
    public void create() throws Exception {
        User user = helper.getNewUser("s.bekberov@gmail.com");
        Member firstMember = new Member();
        firstMember.setUserId(user.getId());
        firstMember.setCreatedBy(user.getEmail());
        firstMember.setRole(Role.ADMIN);
        MvcResult firstMvcResult = super.create(URL_TEMPLATE, firstMember);

        Member secondMember = new Member();
        secondMember.setUserId(user.getId());
        secondMember.setCreatedBy(user.getEmail());
        MvcResult secondMvcResult = super.create(URL_TEMPLATE, secondMember);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), firstMvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(firstMvcResult, "$.id")),
                () -> assertEquals(firstMember.getCreatedBy(), getValue(firstMvcResult, "$.createdBy")),
                () -> assertNull(getValue(firstMvcResult, "$.updatedBy")),
                () -> assertEquals(firstMember.getCreatedDate().withNano(0).toString(), getValue(firstMvcResult, "$.createdDate")),
                () -> assertNull(getValue(firstMvcResult, "$.updatedDate")),
                () -> assertEquals(user.getId().toString(), getValue(firstMvcResult, "$.userId")),
                () -> assertEquals(firstMember.getRole().toString(), getValue(firstMvcResult, "$.role")),
                () -> assertEquals(HttpStatus.CREATED.value(), secondMvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(secondMvcResult, "$.id")),
                () -> assertEquals(secondMember.getCreatedBy(), getValue(secondMvcResult, "$.createdBy")),
                () -> assertNull(getValue(secondMvcResult, "$.updatedBy")),
                () -> assertEquals(secondMember.getCreatedDate().withNano(0).toString(), getValue(secondMvcResult, "$.createdDate")),
                () -> assertNull(getValue(secondMvcResult, "$.updatedDate")),
                () -> assertEquals(user.getId().toString(), getValue(secondMvcResult, "$.userId")),
                () -> assertEquals(Role.GUEST.toString(), getValue(secondMvcResult, "$.role")));
    }

    @Test
    public void createFailure() throws Exception {
        Member entity = new Member();
        MvcResult mvcResult = super.create(URL_TEMPLATE, entity);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void findAll() throws Exception {
        Member firstMember = helper.getNewMember("s.bekberov@gmail.com");
        Member secondMember = helper.getNewMember("bekberov@gmail.com");
        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
        List<Member> testMembers = helper.getMembersArray(mvcResult);

        assertAll(() -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()), () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()), () -> assertTrue(testMembers.contains(firstMember)), () -> assertTrue(testMembers.contains(secondMember)));
    }

    @Test
    public void findById() throws Exception {
        Member member = helper.getNewMember("s.bekberov@gmail.com");
        MvcResult mvcResult = super.getById(URL_TEMPLATE, member.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(member.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(member.getCreatedDate().withNano(0).toString(), getValue(mvcResult, "$.createdDate")),
                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(member.getUserId().toString(), getValue(mvcResult, "$.userId")),
                () -> assertEquals(member.getRole().toString(), getValue(mvcResult, "$.role")));
    }

    @Test
    public void findByIdFailure() throws Exception {
        MvcResult mvcResult = super.getById(URL_TEMPLATE, UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteById() throws Exception {
        Member member = helper.getNewMember("s.bekberov@gmail.com");
        MvcResult mvcResult = super.delete(URL_TEMPLATE, member.getId());
        MvcResult deleteMvcResult = super.getAll(URL_TEMPLATE);
        List<Member> testMembers = helper.getMembersArray(deleteMvcResult);

        assertAll(() -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()), () -> assertFalse(testMembers.contains(member)));
    }

    @Test
    public void deleteByIdFailure() throws Exception {
        MvcResult mvcResult = super.delete(URL_TEMPLATE, UUID.randomUUID());

        assertAll(() -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus()));
    }

    @Test
    public void update() throws Exception {
        Member member = helper.getNewMember("s.bekberov@gmail.com");
        member.setUpdatedBy(member.getCreatedBy());
        member.setRole(Role.ADMIN);
        MvcResult mvcResult = super.update(URL_TEMPLATE, member.getId(), member);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvcResult, "$.id")),
                () -> assertEquals(member.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
                () -> assertEquals(member.getUpdatedBy(), getValue(mvcResult, "$.updatedBy")),
                () -> assertEquals(member.getCreatedDate().withNano(0).toString(), getValue(mvcResult, "$.createdDate")),
                () -> assertEquals(member.getCreatedDate().withNano(0).toString(), getValue(mvcResult, "$.updatedDate")),
                () -> assertEquals(member.getUserId().toString(), getValue(mvcResult, "$.userId")),
                () -> assertEquals(member.getRole().toString(), getValue(mvcResult, "$.role")));
    }

    @Test
    public void updateFailure() throws Exception {
        Member firstMember = helper.getNewMember("s.bekberov@gmail.com");
        firstMember.setUpdatedBy(firstMember.getCreatedBy());
        firstMember.setId(UUID.randomUUID());
        MvcResult firstMvcResult = super.update(URL_TEMPLATE, firstMember.getId(), firstMember);
        assertAll(() -> assertEquals(HttpStatus.NOT_FOUND.value(), firstMvcResult.getResponse().getStatus()));
    }
}
