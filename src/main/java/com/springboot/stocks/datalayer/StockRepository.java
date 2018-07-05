package com.springboot.stocks.datalayer;

import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class StockRepository extends AbstractRepository {

    private void checkForSuffienctSearchCriteria(final StockDAO stockDAO) {
        if (stockDAO.getSymbol() == null &&
                stockDAO.getPrice() == null &&
                stockDAO.getVolume() == null &&
                stockDAO.getDate() == null
                ) {
            throw new IllegalArgumentException("The stock you provided was null or blank");
        }
    }

    public List<StockDAO> list() {
        return list(StockDAO.class);
    }

    public StockDAO findBy(StockDAO stockDAO) {
        List<StockDAO> ts = findAllBy(stockDAO);
        return (ts.isEmpty() ? null : ts.get(0));
    }

    public List<StockDAO> findAllBy(StockDAO stockDAO) {
        checkForSuffienctSearchCriteria(stockDAO);

        TypedQuery<StockDAO> query = getEntityManager().createQuery(buildSqlQuery(StockDAO.class, stockDAO), StockDAO.class);
        addQueryParams(stockDAO, query);

        return query.getResultList();
    }

    private <T> String buildSqlQuery(final Class<T> tClass, final StockDAO stockDAO) {
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

        sql = sql.substring(0 , sql.length() - 4);
        return sql;
    }

    private <T> void addQueryParams(final StockDAO stockDAO, final TypedQuery<T> query) {
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
