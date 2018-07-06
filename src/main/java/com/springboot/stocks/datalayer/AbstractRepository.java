package com.springboot.stocks.datalayer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

public abstract class AbstractRepository {

    @PersistenceContext
    private EntityManager entityManager;

    EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    public void persist(Object entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void remove(Object entity) {
        entityManager.remove(entity);
    }

    @Transactional
    public <T> T merge(T entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public <T> List<T> list(Class<T> tClass) {
        String sql = "SELECT t FROM " + tClass.getName() + " t";
        List<T> ts = getEntityManager().createQuery(sql, tClass).getResultList();

        return ts;
    }

    public <T> T find(Class<T> tClass, Long id) {
        String sql = "SELECT t FROM " + tClass.getName() + " t WHERE t.id = :id";
        TypedQuery<T> query = getEntityManager().createQuery(sql, tClass);
        query.setParameter("id", id);

        List<T> ts = query.getResultList();
        return (ts.isEmpty() ? null : ts.get(0));
    }
}
