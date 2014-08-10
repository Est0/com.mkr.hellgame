package com.mkr.hellgame.hell.impl.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SuppressWarnings("unchecked")
public class DbCrudDaoBase<T> {
    private Class<T> entityClass;
    @Autowired
    protected SessionFactory sessionFactory;

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
        return (T)sessionFactory.getCurrentSession().load(entityClass, id);
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
