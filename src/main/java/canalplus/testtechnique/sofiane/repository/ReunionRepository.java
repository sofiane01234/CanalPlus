package canalplus.testtechnique.sofiane.repository;

import canalplus.testtechnique.sofiane.domain.Reunion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReunionRepository extends CrudRepository<Reunion, Long> {
    @Query("from Reunion e where not(e.end < :from and e.start > :to)")
    public List<Reunion> findBetween(@Param("from") LocalDateTime start, @Param("to") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end);
}