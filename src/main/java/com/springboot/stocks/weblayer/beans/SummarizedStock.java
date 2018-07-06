package com.springboot.stocks.weblayer.beans;

import java.util.Date;

public class SummarizedStock {

    private Long id;
    private String modifierSymbol;
    private Double highPrice;
    private Double lowPrice;
    private Double closingPrice;
    private Date modifierDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModifierSymbol() {
        return modifierSymbol;
    }

    public void setModifierSymbol(String modifierSymbol) {
        this.modifierSymbol = modifierSymbol;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(Double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public Date getModifierDate() {
        return modifierDate;
    }

    public void setModifierDate(Date modifierDate) {
        this.modifierDate = modifierDate;
    }
}
