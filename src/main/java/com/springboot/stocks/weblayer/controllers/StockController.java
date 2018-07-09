package com.springboot.stocks.weblayer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.stocks.datalayer.stock_quotes;
import com.springboot.stocks.datalayer.StockDAOMapper;
import com.springboot.stocks.datalayer.StockRepository;
import com.springboot.stocks.weblayer.beans.StockBean;
import com.springboot.stocks.weblayer.beans.StockBeanMapper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StockController {

    private final StockBeanMapper stockBeanMapper;
    private final StockRepository stockRepository;
    private final StockDAOMapper stockDAOMapper;

    @Autowired
    public StockController(StockBeanMapper stockBeanMapper, StockRepository stockRepository, StockDAOMapper stockDAOMapper) {
        this.stockBeanMapper = stockBeanMapper;
        this.stockRepository = stockRepository;
        this.stockDAOMapper = stockDAOMapper;
    }

    @RequestMapping(
            value = "/stocks",
            produces = "application/json",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String getStock(
            @RequestParam(value = "symbol", required = false) String symbol,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "volume", required = false) Integer volume,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date
    ) {
        List<stock_quotes> stockDAOs;
        JSONArray jsonArray = new JSONArray();
        Double highPrice = 0.00;
        Double lowPrice = 10000.00;
        Integer totalVolume = 0;
        int counter = 0;

        if (symbol != null || price != null || volume != null || date != null) {
            stock_quotes stockToSearchFor = new stock_quotes(symbol, price, volume, date);
            stockDAOs = stockRepository.findAllBy(stockToSearchFor);
        } else {
            stockDAOs = stockRepository.list();
        }

        while (counter < stockDAOs.size() - 1){
            counter++;
            stock_quotes stock_quotes = stockDAOs.get(counter);
            Double priceToCheck = stock_quotes.getPrice();

            totalVolume += stock_quotes.getVolume();
            if (priceToCheck > highPrice){
                highPrice = priceToCheck;
            }
            if (priceToCheck < lowPrice) {
                lowPrice = priceToCheck;
            }
        }

        jsonArray.put(highPrice);
        jsonArray.put(lowPrice);
        jsonArray.put(totalVolume);

        return String.valueOf(jsonArray);
    }
}