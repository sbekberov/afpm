package spd.trello.integrationalTests;


import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserIntegrationTest extends AbstractIntegrationTest<User> {

    String URL_TEMPLATE = "/users";

    @Test
    void getByIdFailure() throws Exception {
        UUID id = UUID.randomUUID();
        MvcResult result = super.getById(URL_TEMPLATE, id);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void getByIdSuccess() throws Exception {
        User entity = new User();
        entity.setFirstName("selim");
        entity.setLastName("bekberov");
        entity.setEmail("bekberov@gmail.com");
        MvcResult ent = super.create(URL_TEMPLATE, entity);
        UUID id = UUID.fromString(JsonPath.read(ent.getResponse().getContentAsString(), "$.id"));
        MvcResult result = super.getById(URL_TEMPLATE, id);
        Assertions.assertNotNull(getValue(result, "$.id"));
    }

    @Test
    void deleteSuccess() throws Exception {
        User entity = new User();
        entity.setFirstName("selim");
        entity.setLastName("bekberov");
        entity.setEmail("bekberov@gmail.com");

        MvcResult ent = super.create(URL_TEMPLATE, entity);
        UUID id = UUID.fromString(JsonPath.read(ent.getResponse().getContentAsString(), "$.id"));

        MvcResult result = super.delete(URL_TEMPLATE, id);
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), super.getById(URL_TEMPLATE, id).getResponse().getStatus())
        );
    }

    @Test
    void deleteFailed() throws Exception {
        UUID id = UUID.randomUUID();
        MvcResult result = super.delete(URL_TEMPLATE, id);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void getAllSuccess() throws Exception {
        MvcResult result = super.getAll(URL_TEMPLATE);
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals("application/json", result.getResponse().getContentType())
        );
    }

    @Test
    void getAllFailure() throws Exception {
        MvcResult result = super.getAll("/wrong");
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void createSuccess() throws Exception {
        User entity = new User();
        entity.setFirstName("selim");
        entity.setLastName("bekberov");
        entity.setEmail("bekberov@gmail.com");
        MvcResult result = super.create(URL_TEMPLATE, entity);
        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus()),
                () -> assertNotNull(getValue(result, "$.id")),
                () -> assertEquals(entity.getFirstName(), getValue(result, "$.firstName")),
                () -> assertEquals(entity.getLastName(), getValue(result, "$.lastName")),
                () -> assertEquals(entity.getEmail(), getValue(result, "$.email"))
        );
    }

    @Test
    void createFailed() throws Exception {
        User entity = new User();
        entity.setFirstName("selim");
        entity.setEmail("bekberov@gmail.com");
        MvcResult result = super.create(URL_TEMPLATE, entity);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void updateSuccess() throws Exception {
        User entity = new User();
        entity.setFirstName("selim");
        entity.setLastName("bekberov");
        entity.setEmail("bekberov@gmail.com");
        super.create(URL_TEMPLATE, entity);
        entity.setFirstName("test");
        entity.setLastName("testtest");
        MvcResult result = super.update(URL_TEMPLATE, entity.getId(), entity);
        assertAll(
                () -> assertNotNull(getValue(result, "$.id")),
                () -> assertEquals(entity.getFirstName(), getValue(result, "$.firstName")),
                () -> assertEquals(entity.getLastName(), getValue(result, "$.lastName")));
    }

    @Test
    void updateFailed() throws Exception {
        User entity = new User();
        entity.setFirstName("selim");
        entity.setLastName("bekberov");
        entity.setEmail("bekberov@gmail.com");
        super.create(URL_TEMPLATE, entity);
        entity.setFirstName(null);
        entity.setLastName("test");
        MvcResult result = super.update(URL_TEMPLATE, entity.getId(), entity);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void updateNotFound() throws Exception {
        User entity = new User();
        entity.setFirstName("selim");
        entity.setLastName("bekberov");
        entity.setEmail("bekberov@gmail.com");
        super.create(URL_TEMPLATE, entity);
        entity.setFirstName("test");
        entity.setLastName("testtest");
        MvcResult result = super.update("/wrong", entity.getId(), entity);

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}