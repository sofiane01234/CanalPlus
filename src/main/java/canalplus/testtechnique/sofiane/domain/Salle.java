package canalplus.testtechnique.sofiane.domain;

import canalplus.testtechnique.sofiane.domain.enums.Equipement;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long Id;
	
	String name;

	Integer max;

    String material;

	@OneToMany
	List<Reservation> reservations;


	public Salle(String name, Integer max) {
		this.name = name;
		this.max = max;
	}


	public Salle() {
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer maximumCapacity) {
		this.max = maximumCapacity;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
