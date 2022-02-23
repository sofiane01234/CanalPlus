package canalplus.testtechnique.sofiane.domain;

import canalplus.testtechnique.sofiane.domain.enums.ReunionType;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    LocalDateTime start;

    LocalDateTime end;

    Integer numberOfPersons;

    ReunionType reunionType;

    String material;


    @OneToOne(cascade = {CascadeType.ALL})
    Reservation reservations;


    public Reunion(String name, LocalDateTime start, LocalDateTime end, Integer numberOfPersons, ReunionType reunionType) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.numberOfPersons = numberOfPersons;
        this.reunionType = reunionType;
    }

    public Reunion(String name, LocalDateTime start, LocalDateTime end, Integer numberOfPersons) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.numberOfPersons = numberOfPersons;
    }

    public Reunion() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String text) {
        this.name = text;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Integer getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(Integer numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public Reservation getReservations() {
        return reservations;
    }

    public void setReservations(Reservation reservations) {
        this.reservations = reservations;
    }

    public ReunionType getReunionType() {
        return reunionType;
    }

    public void setReunionType(ReunionType reunionType) {
        this.reunionType = reunionType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
