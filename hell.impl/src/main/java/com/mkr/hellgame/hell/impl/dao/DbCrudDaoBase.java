package com.mkr.hellgame.hell.impl.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SuppressWarnings("unchecked")
public class DbCrudDaoBase<T> {
    private Class<T> entityClass;
    @Autowired
    private SessionFactory sessionFactory;

    public DbCrudDaoBase(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)

    public T getById(int id) {
/*
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        @SuppressWarnings("unchecked")
        T result = (T)session.get(entityClass, id);
        transaction.commit();
        return result;
*/
        return (T)sessionFactory.getCurrentSession().get(entityClass, id);
    }

    public void create(T value) {
        sessionFactory.getCurrentSession().persist(value);
    }

    public void update(T value) {
        sessionFactory.getCurrentSession().merge(value);
    }

    public void delete(T value) {
        sessionFactory.getCurrentSession().delete(value);
    }
}
