package com.springboot.stocks.weblayer.beans;

import com.springboot.stocks.datalayer.stock_quotes;
import org.springframework.stereotype.Component;

@Component
public class StockBeanMapper {

    public StockBean toBean(stock_quotes stockDAO) {
        StockBean stockBean = new StockBean();

        stockBean.setId(stockDAO.getId());
        stockBean.setSymbol(stockDAO.getSymbol());
        stockBean.setPrice(stockDAO.getPrice());
        stockBean.setVolume(stockDAO.getVolume());
        stockBean.setDate(stockDAO.getDate());

        return stockBean;
    }
}
