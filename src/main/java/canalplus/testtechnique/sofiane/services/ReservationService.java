package canalplus.testtechnique.sofiane.services;

import canalplus.testtechnique.sofiane.domain.Reservation;
import canalplus.testtechnique.sofiane.domain.Reunion;
import canalplus.testtechnique.sofiane.domain.Salle;

import java.util.List;

public interface ReservationService {

    Reservation createReservation(Reunion reunion, List<Salle> salleList);

    List<Salle> getSallesLibres(Reunion reunion);

    Iterable<Reservation> getAllReservations();

    void deleteReservation(Long id);
}
