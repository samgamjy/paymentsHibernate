package by.academy.it.dao;


import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;


public class BaseDao<T> implements Dao<T> {
    private static Logger log = Logger.getLogger(BaseDao.class);
//    private Transaction transaction = null;


    public BaseDao() {

    }

    public void save(T t) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            session.save(t);
            log.info("save(t):" + t);
//            transaction.commit();
            log.info("Save (commit):" + t);


        } catch (HibernateException e) {
            log.error("Error save PERSON in Dao" + e);
//            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public void saveOrUpdate(T t) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            session.saveOrUpdate(t);
            log.info("saveOrUpdate(t):" + t);
//            transaction.commit();
            log.info("Save or update (commit):" + t);
        } catch (HibernateException e) {
            log.error("Error save or update PERSON in Dao" + e);
//            transaction.rollback();
            throw new DaoException(e);
        }


    }

    public T get(Serializable id) throws DaoException {
        log.info("Get class by id:" + id);
        T t = null;
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            t = (T) session.get(getPersistentClass(), id);
//            transaction.commit();
            log.info("get clazz:" + t);
        } catch (HibernateException e) {
//            transaction.rollback();
            log.error("Error get " + getPersistentClass() + " in Dao" + e);
            throw new DaoException(e);
        }
        return t;
    }

    public List<T> getAll() throws DaoException {
        List<T> list = Collections.emptyList();
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            Criteria crit = session.createCriteria(getPersistentClass());
            crit.addOrder(Order.desc("id"));
            list = (List<T>) crit.list();
//            transaction.commit();
            log.info("getAll, size=" + list.size());
        } catch (HibernateException e) {
//            transaction.rollback();
            log.error("Error get " + getPersistentClass() + " in Dao" + e);
            throw new DaoException(e);
        }
        return list;
    }

    public T load(Serializable id) throws DaoException {
        log.info("Load class by id:" + id);
        T t = null;
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            t = (T) session.load(getPersistentClass(), id);
            log.info("load() clazz:" + t);
            session.isDirty();
//            transaction.commit();
        } catch (HibernateException e) {
            log.error("Error load() " + getPersistentClass() + " in Dao" + e);
//            transaction.rollback();
            throw new DaoException(e);
        }
        return t;
    }

    public void delete(T t) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            session.delete(t);
//            transaction.commit();
            log.info("Delete:" + t);
        } catch (HibernateException e) {
            log.error("Error save or update PERSON in Dao" + e);
//            transaction.rollback();
            throw new DaoException(e);
        }
    }

    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

 /*   public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
*/}
