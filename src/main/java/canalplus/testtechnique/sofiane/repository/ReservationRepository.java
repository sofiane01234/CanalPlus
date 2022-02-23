package canalplus.testtechnique.sofiane.repository;

import canalplus.testtechnique.sofiane.domain.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    @Query("from Reservation e where not(e.endTime < :from and e.startTime > :to)")
    public List<Reservation> findBetween(@Param("from") LocalDateTime startTime, @Param("to") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endTime);
}