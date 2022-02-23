package canalplus.testtechnique.sofiane.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest
public class ReservationTest {

    @Autowired
    private TestEntityManager entityManager;

    private Reservation r1;

    @PostConstruct
    public void setUp() {
        r1 = getDefault();
    }

    @Test
    @DisplayName("sauvegarder une reservation dans la base de données")
    void saveReservation() {
        Reservation savedReservation = this.entityManager.persist(r1);
        assertThat(savedReservation.getEndTime(), notNullValue());
        assertThat(savedReservation.getStartTime(), notNullValue());

    }

    @Test
    @DisplayName("Ajouter une Salle a une Reservation")
    void addSalleToReservation() {
        r1.setSalle(new Salle("Salle 1", 5));
        Reservation savReservation = this.entityManager.persist(r1);
        assertThat(savReservation.getSalle(), notNullValue());
    }

    private static Reservation getDefault() {
        return new Reservation(LocalDateTime.of(2019, 12, 11, 2, 2), LocalDateTime.of(2019, 12, 11, 3, 2));
    }

}
