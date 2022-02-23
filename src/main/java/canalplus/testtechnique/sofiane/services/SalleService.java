package canalplus.testtechnique.sofiane.services;

import canalplus.testtechnique.sofiane.domain.Reunion;
import canalplus.testtechnique.sofiane.domain.Salle;
import canalplus.testtechnique.sofiane.domain.enums.Equipement;
import canalplus.testtechnique.sofiane.domain.enums.ReunionType;

import java.util.Map;
import java.util.Set;


public interface SalleService {

    Iterable<Salle> getAllSalles();

    Set<Equipement> getEquipementsManquants(ReunionType reunionType, Salle salle);

    Map<Equipement, Integer> getNombreEquipementsAmovibles(Reunion reunion);
}
