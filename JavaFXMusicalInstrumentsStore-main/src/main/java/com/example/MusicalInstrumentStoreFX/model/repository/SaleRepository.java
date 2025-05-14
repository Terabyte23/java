package com.example.MusicalInstrumentStoreFX.model.repository;

import com.example.MusicalInstrumentStoreFX.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.date BETWEEN :startDate AND :endDate")
    List<Sale> findSalesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT s.instrument.title, COUNT(s) FROM Sale s WHERE s.date BETWEEN :startDate AND :endDate GROUP BY s.instrument.title ORDER BY COUNT(s) DESC")
    List<Object[]> getSalesRanking(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT s.instrument.title, COUNT(s) FROM Sale s GROUP BY s.instrument.title ORDER BY COUNT(s) DESC")
    List<Object[]> getAllTimeSalesRanking();

    List<Sale> findSalesByDateBetween(LocalDate startDate, LocalDate endDate);
}
