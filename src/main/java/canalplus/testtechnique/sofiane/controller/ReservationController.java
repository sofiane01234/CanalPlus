
package canalplus.testtechnique.sofiane.controller;

import canalplus.testtechnique.sofiane.domain.Reservation;
import canalplus.testtechnique.sofiane.domain.Reunion;
import canalplus.testtechnique.sofiane.domain.Salle;
import canalplus.testtechnique.sofiane.exception.NotFoundException;
import canalplus.testtechnique.sofiane.services.ReservationService;
import canalplus.testtechnique.sofiane.services.ReunionService;
import canalplus.testtechnique.sofiane.services.SalleService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    SalleService salleService;

    @Autowired
    ReunionService reunionService;


    @RequestMapping("/api")
    @ResponseBody
    String home() {
        return "Welcome CANAL+ !";
    }

    @RequestMapping("/api/salles")
    Iterable<Salle> salles() {
        return salleService.getAllSalles();
    }

    @RequestMapping("/api/reunions")
    Iterable<Reunion> reunions() {
        return reunionService.getAllReunions();
    }

    @PostMapping("/api/reunions/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Reservation createReunion(@RequestBody Reunion reunion) {
        List<Salle> salleList = reservationService.getSallesLibres(reunion);
        if (!salleList.isEmpty()) {
            return reservationService.createReservation(reunion, salleList);
        } else {
            throw new NotFoundException("Aucune Salle disponible ");
        }
    }

    @RequestMapping("/api/reservations")
    Iterable<Reservation> reservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping("/api/reunions/delete")
    @Transactional
    void deleteEvent(@RequestBody Params params) {
        reservationService.deleteReservation(params.id);
    }

    public static class Params {
        public Long id;
    }

}