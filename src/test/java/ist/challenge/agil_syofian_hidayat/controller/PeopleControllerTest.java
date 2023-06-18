package ist.challenge.agil_syofian_hidayat.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PeopleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getPeople() throws Exception {
        mvc.perform(MockMvcRequestBuilders
  			.get("/api/v1/people/")
  			.accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    void getPeopleByName() throws Exception {
        String name = "Luke Skywalker";
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/people?name="+name)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}