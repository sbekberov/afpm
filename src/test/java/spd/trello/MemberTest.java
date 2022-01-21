package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.Member;
import spd.trello.domain.Role;
import spd.trello.domain.User;
import spd.trello.repository.MemberRepository;
import spd.trello.service.MemberService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static spd.trello.Helper.getNewUser;

public class MemberTest extends BaseTest{
    private final MemberService service;

    public MemberTest(){
        this.service = new MemberService(new MemberRepository(dataSource));
    }
    @Test
    public void successCreate() {
        User user = getNewUser("test@gmail");
        Member testMember = service.create(user, Role.MEMBER);
        assertNotNull(testMember);
        assertAll(
                () -> assertEquals("test@gmail", testMember.getCreatedBy()),
                () -> assertEquals(Date.valueOf(LocalDate.now()), testMember.getCreatedDate()),
                () -> assertEquals(Role.MEMBER, testMember.getRole()),
                () -> assertEquals(user.getId(), testMember.getUsersId())
        );
    }
    @Test
    public void testFindAll() {
        User user = getNewUser("test@gmail");
        Member testFirstMember = service.create(user, Role.MEMBER);
        Member testSecondMember = service.create(user, Role.ADMIN);
        assertNotNull(testFirstMember);
        assertNotNull(testSecondMember);
        List<Member> testMembers = service.getAll();
        assertAll(
                () -> assertTrue(testMembers.contains(testFirstMember)),
                () -> assertTrue(testMembers.contains(testSecondMember))
        );
    }
    @Test
    public void testFindById() {
        UUID id = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(id),
                "no exception"
        );
        assertEquals("Member with ID: " + id + " doesn't exists", ex.getMessage());
    }
    @Test
    public void testDelete() {
        User user = getNewUser("test@gmail");
        Member testMember = service.create(user, Role.MEMBER);
        assertNotNull(testMember);
        UUID id = testMember.getId();
        assertAll(
                () -> assertTrue(service.delete(id)),
                () -> assertFalse(service.delete(id))
        );
    }
}
