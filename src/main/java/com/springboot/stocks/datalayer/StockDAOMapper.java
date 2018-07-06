package com.springboot.stocks.datalayer;

import com.springboot.stocks.weblayer.beans.StockBean;
import org.springframework.stereotype.Component;

@Component
public class StockDAOMapper {

    public stock_quotes toDAO(StockBean stockBean) {
        stock_quotes stockDAO = new stock_quotes();
        updateDAO(stockBean, stockDAO);

        return stockDAO;
    }

    public void updateDAO(StockBean stockBean, stock_quotes stockDAOs) {

        if (stockBean.getSymbol() != null) {
            stockDAOs.setSymbol(stockBean.getSymbol());
        }
        if (stockBean.getPrice() != null) {
            stockDAOs.setPrice(stockBean.getPrice());
        }
        if (stockBean.getVolume() != null) {
            stockDAOs.setVolume(stockBean.getVolume());
        }
        if (stockBean.getDate() != null) {
            stockDAOs.setDate(stockBean.getDate());
        }
    }
}
