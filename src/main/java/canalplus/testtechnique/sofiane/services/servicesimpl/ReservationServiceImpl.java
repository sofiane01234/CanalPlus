package canalplus.testtechnique.sofiane.services.servicesimpl;

import canalplus.testtechnique.sofiane.domain.Reservation;
import canalplus.testtechnique.sofiane.domain.Reunion;
import canalplus.testtechnique.sofiane.domain.Salle;
import canalplus.testtechnique.sofiane.domain.enums.Equipement;
import canalplus.testtechnique.sofiane.domain.enums.ReunionType;
import canalplus.testtechnique.sofiane.exception.NotFoundException;
import canalplus.testtechnique.sofiane.repository.ReservationRepository;
import canalplus.testtechnique.sofiane.repository.SalleRepository;
import canalplus.testtechnique.sofiane.services.ReservationService;
import canalplus.testtechnique.sofiane.services.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    SalleRepository salleRepository;

    @Autowired
    SalleService salleService;


    @Override
    public Reservation createReservation(Reunion reunion, List<Salle> salleList) {

        Reservation reservation = new Reservation(reunion.getStart(), reunion.getEnd());
        reservation.setReunion(reunion);
        Map<Set<Equipement>, Salle> equipementBySalle = new HashMap<>();
        Optional<Salle> salleAReserver;


        if (ReunionType.getEquipementsNecessaires(reunion.getReunionType()).isEmpty()) {
            Optional<Salle> salleVide = getSalleVide(salleList);
            salleVide.ifPresentOrElse(
                    reservation::setSalle,
                    () -> {
                        throw new NotFoundException("Aucune Salle disponible");
                    }
            );
        } else {
            salleList.forEach(salle -> {
                Set<Equipement> equipements = salleService.getEquipementsManquants(reunion.getReunionType(), salle);
                equipementBySalle.put(equipements,
                        getSalleVide(salleList)
                                .get());
            });
            salleAReserver = getSalleAvecMinimumEquipements(equipementBySalle, reunion);
            salleAReserver.ifPresentOrElse(
                    value -> reservation.setSalle(value),
                    () -> {
                        throw new NotFoundException("Aucune Salle disponible");
                    }
            );
        }
        return reservationRepository.save(reservation);

    }

    public Optional<Salle> getSalleAvecMinimumEquipements(Map<Set<Equipement>, Salle> equipSalle, Reunion reunion) {
        List<Set<Equipement>> ListEquipementsSallesDispo = listEquipementsdansLeStock(equipSalle,
                reunion);
        if (!ListEquipementsSallesDispo.isEmpty()) {
            return Optional.of(equipSalle.get(ListEquipementsSallesDispo.get(0)));
        }
        return Optional.empty();
    }

    public List<Set<Equipement>> listEquipementsdansLeStock(Map<Set<Equipement>, Salle> equipSalle,
                                                            Reunion reunion) {
        List<Set<Equipement>> sallesAvecMinEquip = getLeMoinsPossibleEquipements(equipSalle);
        return calculerLeMinimumEquipementsFinal(reunion, sallesAvecMinEquip);
    }

    @Override
    public List<Salle> getSallesLibres(Reunion reunion) {
        Iterable<Reservation> reservations = reservationRepository.findAll();
        Iterable<Salle> salles = salleRepository.findAll();
        List<Salle> sallesNonAvailables = new LinkedList<>();

        reservations.forEach(reservation -> {
            if (reservationOverlaps(reservation, reunion.getStart().minusHours(1), reunion.getEnd())) {
                sallesNonAvailables.add(reservation.getSalle());
            }

        });


        return StreamSupport.stream(salles.spliterator(), false).filter(room -> sallesNonAvailables.stream()
                .noneMatch(notAvailableRoom -> notAvailableRoom.equals(room)))
                .filter(res -> res.getMax() * 0.7 >= reunion.getNumberOfPersons()).collect(Collectors.toList());
    }

    public Optional<Salle> getSalleVide(final List<Salle> salles) {
        if (salles != null) {
            return salles.stream().min(Comparator.comparing(Salle::getMax)).isEmpty() ?
                    salles.stream()
                            .filter(salle -> salle.getMaterial() == null || salle.getMaterial().isEmpty())
                            .min(Comparator.comparing(Salle::getMax)) : salles.stream().min(Comparator.comparing(Salle::getMax));

        }
        return Optional.empty();
    }


    @Override
    public Iterable<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(Long id) {
        this.reservationRepository.deleteById(id);
    }


    private boolean reservationOverlaps(final Reservation reservation, final LocalDateTime startTime,
                                        final LocalDateTime endTime) {
        return startTime.isBefore(reservation.getEndTime())
                && endTime.isAfter(reservation.getStartTime());
    }


    public List<Set<Equipement>> getLeMoinsPossibleEquipements(Map<Set<Equipement>, Salle> toolsAndRooms) {
        Map<Integer, List<Set<Equipement>>> nbEquipAbs = new HashMap<>();
        var ref = new Object() {
            int min = Integer.MAX_VALUE;
        };
        Set<Set<Equipement>> sets = toolsAndRooms.keySet();
        for (Set<Equipement> set : sets) {
            if (nbEquipAbs.get(set.size()) == null) {
                nbEquipAbs.put(set.size(), List.of(set));
            } else {
                List<Set<Equipement>> sets1 = new ArrayList(nbEquipAbs.get(set.size()));
                sets1.add(set);
                nbEquipAbs.put(set.size(), sets1);
            }
            if (set.size() != 0 && set.size() < ref.min) {
                ref.min = set.size();
            }
        }
        return nbEquipAbs.get(ref.min);
    }

    public List<Set<Equipement>> calculerLeMinimumEquipementsFinal(Reunion reunion,
                                                                   List<Set<Equipement>> sallesAvecMinEquip) {
        List<Set<Equipement>> res = new ArrayList<>();
        Map<Equipement, Integer> stock = salleService.getNombreEquipementsAmovibles(reunion);
        if (sallesAvecMinEquip != null && !sallesAvecMinEquip.isEmpty()) {
            for (Set<Equipement> set : sallesAvecMinEquip) {
                AtomicBoolean available = new AtomicBoolean(true);
                for (Equipement s : set) {
                    if (stock.get(s) == 0) {
                        available.set(false);
                    }
                }
                if (available.get()) {
                    res.add(set);
                }
            }
        }
        return res;
    }

}
