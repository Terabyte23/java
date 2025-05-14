package com.example.MusicalInstrumentStoreFX.model.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Instruments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int publicationYear;
    private int quantity;
    private int count;
    private Integer orderNumber;
    private double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "instrument_brands",
            joinColumns = @JoinColumn(name = "instrument_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private Set<Brand> brands = new HashSet<>();

    public Instruments() {}

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Brand> getBrand() {
        return brands;
    }

    public void setBrand(Set<Brand> brands) {
        this.brands = brands;
    }

    // equals, hashCode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instruments)) return false;
        Instruments that = (Instruments) o;
        return publicationYear == that.publicationYear &&
                quantity == that.quantity &&
                count == that.count &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(brands, that.brands) &&
                Objects.equals(orderNumber, that.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, publicationYear, quantity, count, orderNumber, price, brands);
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brands=" + brands +
                ", publicationYear=" + publicationYear +
                ", quantity=" + quantity +
                ", count=" + count +
                ", orderNumber=" + orderNumber +
                ", price=" + price +
                '}';
    }
}
