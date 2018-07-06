package com.springboot.stocks.datalayer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stock_quotes")
public class stock_quotes {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "price")
    private Double price;
    @Column(name = "volume")
    private Integer volume;
    @Column(name = "date")
    private Date date;

    public stock_quotes() { }

    public stock_quotes(String symbol, Double price, Integer volume, Date date) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.date = date;
    }

    public stock_quotes(String symbol) {
        this.symbol = symbol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
