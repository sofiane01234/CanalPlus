package canalplus.testtechnique.sofiane.services.servicesimpl;


import canalplus.testtechnique.sofiane.domain.Reservation;
import canalplus.testtechnique.sofiane.domain.Reunion;
import canalplus.testtechnique.sofiane.domain.Salle;
import canalplus.testtechnique.sofiane.domain.enums.ReunionType;
import canalplus.testtechnique.sofiane.exception.NotFoundException;
import canalplus.testtechnique.sofiane.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationServiceImpl reservationService;

    @Test
    @DisplayName("doit sauvegarder une Reunion en fonction des dispo ")
    void doitSauvegarderReunion() {
        final Reunion e = new Reunion("Reunion", LocalDateTime.of(2019, 12, 11, 2, 2), LocalDateTime.of(2019, 12, 11, 3, 2), 4, ReunionType.RS);
        when(reservationRepository.save(any())).then(invocation -> invocation.getArgument(0));

        Reservation reservation = reservationService.createReservation(e, Arrays.asList(new Salle("Salle 1", 15), new Salle("Salle 2", 14)));

        assertThat(reservation.getSalle().getName(), is("Salle 2"));
        assertThat(reservation.getSalle().getMax(), is(14));
        assertThat(reservation.getEndTime(), notNullValue());
    }


    @Test
    @DisplayName("doit lever une exception si y'a pas de salles dispo")
    void doitLeverExceptionSiPasdeDispo() {
        final Reunion e = new Reunion("Reunion", LocalDateTime.of(2019, 12, 11, 2, 2), LocalDateTime.of(2019, 12, 11, 3, 2), 24, ReunionType.RS);

        assertThrows(NotFoundException.class, () -> reservationService.createReservation(e, null));

    }

    @Test
    @DisplayName("doit lever une exception si y'a pas de salles dispo")
    void recuperereLaSalleVideAvecMoinsDeRessources() {

        Salle salle1 = new Salle("1", 10);
        Salle salle2 = new Salle("3", 15);
        Salle salle3 = new Salle("2", 17);
        Salle salle4 = new Salle("4", 104);
        Salle salle5 = new Salle("5", 11);
        Salle salle6 = new Salle("6", 19);
        Optional<Salle> s = reservationService.getSalleVide(List.of(salle1, salle2, salle3, salle4, salle5, salle6));
        assertTrue(!s.isEmpty());
        assertThat(salle1, is(s.get()));
    }
}
