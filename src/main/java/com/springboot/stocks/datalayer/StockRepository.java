package com.springboot.stocks.datalayer;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class StockRepository extends AbstractRepository {

    public List<stock_quotes> list() {
        return list(stock_quotes.class);
    }

    public List<stock_quotes> findAllBy(stock_quotes stockDAO) {
        TypedQuery<stock_quotes> query = getEntityManager().createQuery(buildSqlQuery(stock_quotes.class, stockDAO), stock_quotes.class);
        addQueryParams(stockDAO, query);
        return query.getResultList();
    }

    private <T> String buildSqlQuery(final Class<T> tClass, final stock_quotes stockDAO) {
        String sql = "SELECT t FROM " + tClass.getName() + " t WHERE";
        if (stockDAO.getSymbol() != null) {
            sql = sql + " upper(t.symbol) = upper(:symbol) AND";
        }
        if (stockDAO.getPrice() != null) {
            sql = sql + " upper(t.price) = upper(:price) AND";
        }
        if (stockDAO.getVolume() != null) {
            sql = sql + " upper(t.volume) = upper(:volume) AND";
        }
        if (stockDAO.getDate() != null) {
            sql = sql + " upper(t.date) = upper(:date) AND";
        }

        sql = sql.substring(0, sql.length() - 4);
        return sql;
    }

    private <T> void addQueryParams(final stock_quotes stockDAO, final TypedQuery<T> query) {
        if (stockDAO.getSymbol() != null) {
            query.setParameter("symbol", stockDAO.getSymbol());
        }
        if (stockDAO.getPrice() != null) {
            query.setParameter("price", stockDAO.getPrice());
        }
        if (stockDAO.getVolume() != null) {
            query.setParameter("volume", stockDAO.getVolume());
        }
        if (stockDAO.getDate() != null) {
            query.setParameter("date", stockDAO.getDate());
        }
    }
}