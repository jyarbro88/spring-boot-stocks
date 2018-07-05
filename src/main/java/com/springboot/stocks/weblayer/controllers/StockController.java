package com.springboot.stocks.weblayer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.stocks.datalayer.StockDAO;
import com.springboot.stocks.datalayer.StockDAOMapper;
import com.springboot.stocks.datalayer.StockRepository;
import com.springboot.stocks.weblayer.beans.StockBean;
import com.springboot.stocks.weblayer.beans.StockBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StockBeanMapper stockBeanMapper;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockDAOMapper stockDAOMapper;

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
        List<StockDAO> stockDAOS;

        if (symbol != null || price != null || volume != null || date != null) {
            StockDAO stockDAOToSearchFor = new StockDAO(symbol, price, volume, date);
            stockDAOS = stockRepository.findAllBy(stockDAOToSearchFor);
        } else {
            stockDAOS = stockRepository.list();
        }

        return stockDAOS.stream().map(stockBeanMapper::toBean).collect(Collectors.toList());
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

        StockDAO stockDAO = stockDAOMapper.toDAO(stockBean);
        stockRepository.persist(stockDAO);

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
        StockDAO foundStockDAO = stockRepository.find(StockDAO.class, stockId);

        // Marshall the JSON String to a Java Bean
        ObjectMapper mapper = new ObjectMapper();
        StockBean stockBean;

        try {
            stockBean = mapper.readValue(stockJson, StockBean.class);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Update the found DAO with changed values in StockBean
        stockDAOMapper.updateDAO(stockBean, foundStockDAO);

        try {
            stockRepository.merge(foundStockDAO);
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
        StockDAO foundStockDAO = stockRepository.find(StockDAO.class, stockId);

        if (foundStockDAO == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            stockRepository.remove(foundStockDAO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
