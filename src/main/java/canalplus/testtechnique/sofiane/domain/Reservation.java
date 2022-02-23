package canalplus.testtechnique.sofiane.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @ManyToOne
    Salle salle;

    @OneToOne(cascade = {CascadeType.ALL})
    Reunion reunion;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Reservation(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reservation() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


}
