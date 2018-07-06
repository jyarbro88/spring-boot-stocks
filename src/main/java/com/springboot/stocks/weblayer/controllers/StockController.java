package com.springboot.stocks.weblayer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.stocks.datalayer.stock_quotes;
import com.springboot.stocks.datalayer.StockDAOMapper;
import com.springboot.stocks.datalayer.StockRepository;
import com.springboot.stocks.weblayer.beans.StockBean;
import com.springboot.stocks.weblayer.beans.StockBeanMapper;
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
            value = "/summarized-data",
            produces = "application/json",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List getSummarizedStock(
            @RequestParam(value = "symbol", required = true) String symbol,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "date", required = true) Date date
    ) {

        ObjectMapper mapper = new ObjectMapper();

        return stockRepository.findBySymbolAndDate(symbol, date);

//        return bySymbol.stream().map(stockBeanMapper::toBean).collect(Collectors.toList());
    }


    @RequestMapping(
            value = "/stocks",
            produces = "application/json",
            method = RequestMethod.GET
    )
    @ResponseBody
    public List<StockBean> getStock(
            @RequestParam(value = "symbol", required = false) String symbol,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "volume", required = false) Integer volume,
            @RequestParam(value = "date", required = false) Date date
    ) {
        List<stock_quotes> stockDAOs;

        if (symbol != null || price != null || volume != null || date != null) {
            stock_quotes stockToSearchFor = new stock_quotes(symbol, price, volume, date);
            stockDAOs = stockRepository.findAllBy(stockToSearchFor);
        } else {
            stockDAOs = stockRepository.list();
        }

        return stockDAOs.stream().map(stockBeanMapper::toBean).collect(Collectors.toList());
    }

    @RequestMapping(
            value = "/stocks",
            produces = "application/json",
            method = RequestMethod.POST
    )
    @ResponseBody
    public ResponseEntity addStock(
            @RequestBody(required = true) String stockJson
    ) {
        ObjectMapper mapper = new ObjectMapper();
        StockBean stockBean;

        try {
            stockBean = mapper.readValue(stockJson, StockBean.class);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        stock_quotes StockDAO = stockDAOMapper.toDAO(stockBean);
        stockRepository.persist(StockDAO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/stocks/{stockId}",
            produces = "application/json",
            method = RequestMethod.PUT
    )
    @ResponseBody
    public ResponseEntity updateStock(
            @RequestBody(required = true) String stockJson,
            @PathVariable(value = "stockId") Long stockId
    ) {
        // Fetch the original data from the database for given ID
        stock_quotes foundStock = stockRepository.find(stock_quotes.class, stockId);

        // Marshall the JSON String to a Java Bean
        ObjectMapper mapper = new ObjectMapper();
        StockBean stockBean;

        try {
            stockBean = mapper.readValue(stockJson, StockBean.class);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Update the found DAO with changed values in StockBean
        stockDAOMapper.updateDAO(stockBean, foundStock);

        try {
            stockRepository.merge(foundStock);
            return new ResponseEntity(HttpStatus.OK);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            value = "/stocks/{stockId}",
            produces = "application/json",
            method = RequestMethod.DELETE
    )
    @ResponseBody
    @Transactional
    public ResponseEntity deleteStock(
            @PathVariable(value = "stockId") Long stockId
    ){
        stock_quotes foundStock = stockRepository.find(stock_quotes.class, stockId);

        if (foundStock == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            stockRepository.remove(foundStock);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}