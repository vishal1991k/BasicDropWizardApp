package com.basic.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class BaseDAO<T> {

    protected final Class<T> dtoClass;
    protected final SessionFactory sessionFactory;

    protected BaseDAO(Class<T> dtoClass, SessionFactory sessionFactory) {
        this.dtoClass = dtoClass;
        this.sessionFactory = sessionFactory;
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    protected <R> R executeInTransaction(SessionAction<R> action) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        try {
            R result = action.execute(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public T update(T dto) {
        return executeInTransaction(session -> {
            session.update(dto);
            return dto;
        });
    }

    public T save(T dto) {
        return executeInTransaction(session -> {
            session.save(dto);
            return dto;
        });
    }

    public T delete(T dto) {
        return executeInTransaction(session -> {
            session.delete(dto);
            return dto;
        });
    }

    public List<T> get(CriteriaQuery<T> criteria) {
        return executeInTransaction(session -> session.createQuery(criteria).getResultList());
    }

    public T get(String key,String value) {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(dtoClass);
        Root<T> root = criteria.from(dtoClass);
        criteria.select(root).where(builder.equal(root.get(key), value));
        List<T> results = get(criteria);
        return results.isEmpty() ? null : results.get(0);
    }

    public List<T> getAll(String key, String value) {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(dtoClass);
        Root<T> root = criteria.from(dtoClass);
        Predicate taxiPredicate = builder.equal(root.get(key), value);
        criteria.select(root).where(taxiPredicate);
        return get(criteria);
    }

    public void delete(String key, String value) {
        T booking = get(key,value);
        if (booking != null) {
            delete(booking);
        }
    }
    @FunctionalInterface
    interface SessionAction<R> {
        R execute(Session session);
    }
}

