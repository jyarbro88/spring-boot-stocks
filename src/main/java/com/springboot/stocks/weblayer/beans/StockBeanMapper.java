package com.springboot.stocks.weblayer.beans;

import com.springboot.stocks.datalayer.StockDAO;
import org.springframework.stereotype.Component;

@Component
public class StockBeanMapper {

    public StockBean toBean(StockDAO stockDAO) {
        StockBean stockBean = new StockBean();

        stockBean.setId(stockDAO.getId());
        stockBean.setSymbol(stockDAO.getSymbol());
        stockBean.setPrice(stockDAO.getPrice());
        stockBean.setVolume(stockDAO.getVolume());
        stockBean.setDate(stockDAO.getDate());

        return stockBean;
    }
}
