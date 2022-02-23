package canalplus.testtechnique.sofiane.controller;


import canalplus.testtechnique.sofiane.domain.Reservation;
import canalplus.testtechnique.sofiane.domain.Reunion;
import canalplus.testtechnique.sofiane.domain.Salle;
import canalplus.testtechnique.sofiane.domain.enums.ReunionType;
import canalplus.testtechnique.sofiane.services.ReservationService;
import canalplus.testtechnique.sofiane.services.ReunionService;
import canalplus.testtechnique.sofiane.services.SalleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservationService reservationService;

    @MockBean
    SalleService salleService;

    @MockBean
    ReunionService reunionService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void findAll_ShouldReturnAllItems() throws Exception {

        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(
                new Reservation(LocalDateTime.of(2019, 12, 11, 2, 2), LocalDateTime.of(2019, 12, 11, 2, 2))
        ));

        String expectedJson = "[{'salle':null,'reunion':null,'startTime':'2019-12-11T02:02:00','endTime':'2019-12-11T02:02:00','id':null}]";

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void create_ShouldCallCreateOnItemServiceAndReturnItem() throws Exception {

        Reunion reunion = new Reunion("Reunion", LocalDateTime.parse("2019-10-11T10:00:00"), LocalDateTime.parse("2019-10-11T11:00:00"), 8, ReunionType.RC);
        Salle salle1 = new Salle("1", 10);
        Salle salle2 = new Salle("3", 15);
        Salle salle3 = new Salle("2", 17);
        Salle salle4 = new Salle("4", 104);
        Salle salle5 = new Salle("5", 11);
        Salle salle6 = new Salle("6", 19);
        when(reservationService.getSallesLibres(any())).thenReturn(List.of(salle1, salle2, salle3, salle4, salle5, salle6));
        when(reservationService.createReservation(reunion, Arrays.asList(new Salle("Salle 1", 15), new Salle("Salle 2", 14))))
                .thenReturn(new Reservation(LocalDateTime.parse("2019-10-11T10:00:00"), LocalDateTime.parse("2019-10-11T11:00:00")));

        RequestBuilder requestBuilder = post("/api/reunions/create")
                .content(itemToJson(reunion))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    private String itemToJson(Reunion reunion) throws JsonProcessingException {
        ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(reunion);
    }

}
