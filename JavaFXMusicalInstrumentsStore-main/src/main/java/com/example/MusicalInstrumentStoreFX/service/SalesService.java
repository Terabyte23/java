package com.example.MusicalInstrumentStoreFX.service;

import com.example.MusicalInstrumentStoreFX.model.entity.Sale;
import com.example.MusicalInstrumentStoreFX.model.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesService {

    private final SaleRepository saleRepository;

    public SalesService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    // Метод для расчета дохода за определенный период
    public double getIncomeForPeriod(LocalDate startDate, LocalDate endDate) {
        List<Sale> sales = saleRepository.findSalesBetweenDates(startDate, endDate);
        return sales.stream()
                .mapToDouble(Sale::getAmount)
                .sum();
    }
}
