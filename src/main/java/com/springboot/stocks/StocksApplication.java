package com.springboot.stocks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.stocks.datalayer.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@SpringBootApplication
public class StocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StockRepository stockRepository) {
        return args -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                URL url = new URL("https://bootcamp-training-files.cfapps.io/week2/week2-stocks.json");
                URL urlRoute = new URL("https://bootcamp-training-files.cfapps.io/week2/week2-stocks.json ");

                InputStream inputStream = new URL("https://bootcamp-training-files.cfapps.io/week2/week2-stocks.json ").openStream();

//                List<StockDAO> stockDAOS = mapper.readValue(inputStream, StockBean.class);
//                stockRepository.persist(stockDAOS);
                System.out.println("Stock saved!");
            } catch (IOException e) {
                System.out.println("Unable to save stocks: " + e.getMessage());
            }
        };
    }
}