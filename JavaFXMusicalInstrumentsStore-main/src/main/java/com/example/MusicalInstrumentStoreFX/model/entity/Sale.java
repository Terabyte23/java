package com.example.MusicalInstrumentStoreFX.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private double amount;

    // Новое поле — это возврат?
    private boolean isReturn;

    @ManyToOne
    private Instruments instrument;

    // --- Геттеры и сеттеры ---

    public Long getId() {
        return id;
    }

    public LocalDate getSaleDate() {
        return date;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.date = saleDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public Instruments getInstrument() {
        return instrument;
    }

    public void setInstrument(Instruments instrument) {
        this.instrument = instrument;
    }
}
