package spd.trello.integrationalTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spd.trello.domain.common.Domain;


import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest<E extends Domain> implements CommonIntegrationTest<E> {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public Object getValue(MvcResult mvcResult, String jsonPath) throws UnsupportedEncodingException {
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), jsonPath);
    }

    @DisplayName("Get all")
    public MvcResult getAll(String URL_TEMPLATE) throws Exception {
        return mockMvc.perform(get(URL_TEMPLATE))
                .andReturn();
    }

    @DisplayName("Save")
    public MvcResult create(String URL_TEMPLATE, E entity) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(URL_TEMPLATE, entity)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andReturn();
    }

    @DisplayName("Delete")
    public MvcResult delete(String URL_TEMPLATE, UUID id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(URL_TEMPLATE + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @DisplayName("Get by Id")
    public MvcResult getById(String URL_TEMPLATE, UUID id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(URL_TEMPLATE + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @DisplayName("Update")
    public MvcResult update(String URL_TEMPLATE, UUID id, E entity) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(URL_TEMPLATE + "/{id}", id , entity)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entity)))
                .andReturn();
    }

}
