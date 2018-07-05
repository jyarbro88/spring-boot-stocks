package com.springboot.stocks.datalayer;

import com.springboot.stocks.weblayer.beans.StockBean;
import org.springframework.stereotype.Component;

@Component
public class StockDAOMapper {

    public StockDAO toDAO(StockBean stockBean) {
        StockDAO stockDAO = new StockDAO();
        updateDAO(stockBean, stockDAO);

        return stockDAO;
    }

    private void updateDAO(StockBean stockBean, StockDAO stockDAO) {

        if (stockBean.getSymbol() != null) {
            stockDAO.setSymbol(stockBean.getSymbol());
        }

        if (stockBean.getPrice() != null) {
            stockDAO.setPrice(stockBean.getPrice());
        }

        if (stockBean.getVolume() != null) {
            stockDAO.setVolume(stockBean.getVolume());
        }

        if (stockBean.getDate() != null) {
            stockDAO.setDate(stockBean.getDate());
        }
    }
}
