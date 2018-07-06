package com.springboot.stocks.datalayer;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class StockRepository extends AbstractRepository {

    public List<stock_quotes> list() {
        return list(stock_quotes.class);
    }

//    public List<stock_quotes> listByMonth(Date startDate, Date endDate) {
//        getEntityManager().createQuery("SELECT e " +
//                "FROM stock_quotes e " +
//                "WHERE e.date BETWEEN :start AND :end")
//                .setParameter("start", startDate, TemporalType.DATE)
//                .setParameter("end", endDate, TemporalType.DATE)
//                .getResultList();
//
//    }

//    public List<stock_quotes> showSummarizedData(String symbol, Date date) {
//        getEntityManager().createQuery("SELECT t FROM stock_quotes t WHERE t.date = :dateToSearch")
//                .setParameter("dateToSearch", date, TemporalType.DATE);
//    }

    public stock_quotes findBy(stock_quotes stockquotes) {
        List<stock_quotes> ts = findAllBy(stockquotes);
        return (ts.isEmpty() ? null : ts.get(0));
    }

    public Query findMinAndMax(stock_quotes stockSymbol) {

        String sqlStatement = "SELECT MIN(price), MAX(price), SUM(volume) FROM stock_quotes WHERE symbol = '" + stockSymbol.getSymbol() + "'";

        return getEntityManager().createQuery(sqlStatement);
    }



//    public Query findData(List<stock_quotes> stockModifier) {
//
//        String sqlStatement = "SELECT MIN(price) AS low_price, MAX(price) AS high_price, SUM(volume) AS volume FROM stock_quotes WHERE symbol = '" + stockModifier.getModifierSymbol() + "' AND date = '" + stockModifier.getModifierDate() + "';";
//
//        return getEntityManager().createQuery(sqlStatement);
//
//    }
//    private <T> String buildAggregatedQuery(final Class<T> tClass, final stock_quotes stockDAO) {
//
//        String sql = "SELECT MIN(price) AS low_price, MAX(price) AS high_price, SUM(volume) AS volume FROM stock_quotes WHERE symbol = 'AAPL' AND date LIKE '2018-06-22%';";
//    }

    private void checkForSufficientSearchCriteria(final stock_quotes stockquotes) {


        if (stockquotes.getSymbol() == null &&
                stockquotes.getPrice() == null &&
                stockquotes.getVolume() == null &&
                stockquotes.getDate() == null
                ) {
            throw new IllegalArgumentException("The stock you provided was null or blank");
        }
    }

    public List<stock_quotes> findAllBy(stock_quotes stockquotes) {
        checkForSufficientSearchCriteria(stockquotes);

        TypedQuery<stock_quotes> query = getEntityManager().createQuery(buildSqlQuery(stock_quotes.class, stockquotes), stock_quotes.class);
        addQueryParams(stockquotes, query);

        return query.getResultList();
    }

    public List<stock_quotes> findSummarizedData(stock_quotes stockquotes) {
        checkForSufficientSearchCriteria(stockquotes);

        TypedQuery<stock_quotes> query = getEntityManager().createQuery(buildSummarizedQuery(stock_quotes.class, stockquotes), stock_quotes.class);
        addQueryParams(stockquotes, query);

        return query.getResultList();

    }

    private <T> String buildSqlQuery(final Class<T> tClass, final stock_quotes stockquotes) {
        String sql = "SELECT t FROM " + tClass.getName() + " t WHERE";
        if (stockquotes.getSymbol() != null) {
            sql = sql + " upper(t.symbol) = upper(:symbol) AND";
        }
        if (stockquotes.getPrice() != null) {
            sql = sql + " upper(t.price) = upper(:price) AND";
        }
        if (stockquotes.getVolume() != null) {
            sql = sql + " upper(t.volume) = upper(:volume) AND";
        }
        if (stockquotes.getDate() != null) {
            sql = sql + " upper(t.date) = upper(:date) AND";
        }

        sql = sql.substring(0, sql.length() - 4);
        return sql;
    }

    private <T> String buildSummarizedQuery(final Class<T> tClass, final stock_quotes stockquotes) {

        return "SELECT MIN(price) AS low_price, MAX(price) AS high_price, SUM(volume) AS volume FROM stock_quotes WHERE symbol = ':symbol' AND date = :date";
//        if (stockquotes.getSymbol() != null) {
//
//            summarizedSql = summarizedSql + " upper(t.price) = upper(:price)";
//        }
    }

    private <T> void addQueryParams(final stock_quotes stockquotes, final TypedQuery<T> query) {
        if (stockquotes.getSymbol() != null) {
            query.setParameter("symbol", stockquotes.getSymbol());
        }
        if (stockquotes.getPrice() != null) {
            query.setParameter("price", stockquotes.getPrice());
        }
        if (stockquotes.getVolume() != null) {
            query.setParameter("volume", stockquotes.getVolume());
        }
        if (stockquotes.getDate() != null) {
            query.setParameter("date", stockquotes.getDate());
        }
    }
}
