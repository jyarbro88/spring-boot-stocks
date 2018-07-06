package com.springboot.stocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.stocks.datalayer.stock_quotes;
import com.springboot.stocks.datalayer.StockRepository;

import java.io.File;

class JsonReader {

    void readJsonFile(StockRepository stockRepository) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        stock_quotes[] stockList = objectMapper.readValue(new File("/Users/joeyarbrough/Projects/Spring-Boot-Stocks/src/main/resources/stocks.json"), stock_quotes[].class);
        int counter = 0;
        while (counter < (stockList.length - 1)) {
            counter++;
            try {
                stockRepository.persist(stockList[counter]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
