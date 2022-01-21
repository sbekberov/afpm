package spd.trello;

import org.junit.jupiter.api.Test;
import spd.trello.domain.User;
import spd.trello.repository.UserRepository;
import spd.trello.service.UserService;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest extends BaseTest{
    private final UserService service;

    public UserTest() {
        service = new UserService(new UserRepository(dataSource));
    }

    @Test
    public void successCreate(){
        User testUser = service.create("Selim", "Bekberov", "s.bekberov@gmail.com");
        assertNotNull(testUser);
        assertAll(
                () -> assertEquals("Selim", testUser.getFirstName()),
                () -> assertEquals("Bekberov", testUser.getLastName()),
                () -> assertEquals("s.bekberov@gmail.com", testUser.getEmail()),
                () -> assertEquals(ZoneId.systemDefault().toString(), testUser.getTimeZone())
        );
    }
    @Test
    public void testFindAll(){
        User testFirstUser = service.create("Selim", "Bekberov", "s.bekberov@gmail.com");
        User testSecondUser = service.create("Selim1", "Bekberov1", "s.bekberov@gmail.com");
        assertNotNull(testFirstUser);
        assertNotNull(testSecondUser);
        List<User> testUsers = service.getAll();
        assertAll(
                () -> assertTrue(testUsers.contains(testFirstUser)),
                () -> assertTrue(testUsers.contains(testSecondUser))
        );
    }
    @Test
    public void testDelete(){
        User testUser = service.create("Selim", "Bekberov", "s.bekberov@gmail.com");
        assertNotNull(testUser);
        UUID id = testUser.getId();
        assertAll(
                () -> assertTrue(service.delete(id)),
                () -> assertFalse(service.delete(id))
        );
    }
    @Test
    public void testUpdate() {
        User user = service.create("Selim", "Bekberov", "s.bekberov@gmail.com");
        assertNotNull(user);
        User testUser = new User();
        testUser.setId(user.getId());
        testUser.setFirstName("Selim1");
        testUser.setLastName("Bekberov1");
        testUser.setEmail("s.bekberovvvv@gmail.com");
        testUser.setTimeZone("Europe/Kyiv");
        service.update(testUser);
        assertAll(
                () -> assertEquals("Selim1", testUser.getFirstName()),
                () -> assertEquals("Bekberov1", testUser.getLastName()),
                () -> assertEquals("s.bekberovvvv@gmail.com", testUser.getEmail()),
                () -> assertEquals("Europe/Kyiv", testUser.getTimeZone())
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
        assertEquals("Users with ID: " + id + " doesn't exists", ex.getMessage());
    }
}
