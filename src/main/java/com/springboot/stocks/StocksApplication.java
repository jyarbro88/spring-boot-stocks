package com.springboot.stocks;

import com.springboot.stocks.datalayer.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StockRepository stockRepository) {
        JsonReader jsonReader = new JsonReader();
        return args -> {

            jsonReader.readJsonFile(stockRepository);
        };
    }
}
