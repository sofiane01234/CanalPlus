package canalplus.testtechnique.sofiane.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@DataJpaTest
public class ReunionTest {

    @Autowired
    private TestEntityManager entityManager;

    private Reunion e1;

    @PostConstruct
    public void setUp() {
        e1 = getDefault();
    }

    @Test
    @DisplayName("Sauvegdarder une réunion")
    void saveReunion() {
        Reunion savedReunion = this.entityManager.persist(e1);
        assertThat(savedReunion.name, is("Reunion"));
    }

    /*@Test
    @DisplayName("Ajouter une Salle a une Reunion")
    void addSalleToReunion() {
        e1.setSalle(new Salle("Salle 1",5));
        Reunion savedReunion = this.entityManager.persist(e1);
        assertThat(savedReunion.getSalle() , notNullValue() );
    }*/

    private static Reunion getDefault() {
        return new Reunion("Reunion", LocalDateTime.of(2019, 12, 11, 2, 2), LocalDateTime.of(2019, 12, 11, 3, 2), 4);
    }

}
