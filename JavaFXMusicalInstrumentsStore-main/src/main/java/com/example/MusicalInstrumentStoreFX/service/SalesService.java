package com.example.MusicalInstrumentStoreFX.service;

import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.model.entity.Sale;
import com.example.MusicalInstrumentStoreFX.model.repository.SaleRepository;
import com.example.MusicalInstrumentStoreFX.viewmodel.InstrumentRatingViewModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
                .filter(sale -> !sale.isReturn()) // Исключаем возвраты
                .mapToDouble(Sale::getAmount)
                .sum();
    }


    public void addSale(Sale sale) {
        saleRepository.save(sale);
    }

    public List<InstrumentRatingViewModel> getInstrumentSalesRating(LocalDate from, LocalDate to) {
        List<Object[]> salesData = saleRepository.getSalesRanking(from, to);

        return salesData.stream()
                .map(data -> {
                    Instruments instrument = (Instruments) data[0];
                    Integer count = ((Number) data[1]).intValue();
                    return new InstrumentRatingViewModel(instrument.getTitle(), count, instrument);
                })
                .collect(Collectors.toList());
    }


}
