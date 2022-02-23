package canalplus.testtechnique.sofiane.IT;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudIT {

    @Autowired
    MockMvc mockMvc;

    private void performFindAllAndExpectJson(String expectedJson) throws Exception {
        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    ;}

    @Test
    public void findAll() throws Exception {
        String expectedJson = "[{'salle':null,'reunion':null,'startTime':'2019-12-11T02:02:00','endTime':'2019-12-11T02:02:00','id':null}]";

        performFindAllAndExpectJson(expectedJson);
    }

}
