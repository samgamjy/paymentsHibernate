package by.academy.it.dao;

import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.entity.Client;
import by.academy.it.utils.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

/**
 * Created by sam on 18.06.2015.
 */
public class ClientDao extends BaseDao<Client> implements IClientDao, IUserDao {

//    private Transaction transaction = null;


    @Override
    public int create(Client client) throws DaoException {
        try {
            saveOrUpdate(client);

        }catch (DaoException e){
            e.printStackTrace();
            throw e;
        }
        return client.getId();

    }

    @Override
    public Client getClientByBankAccountID(int bankAccountID) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            String hql = "select C from by.academy.it.entity.Client C where C.bankAccountID=:bankAccountID";
            Query query = session.createQuery(hql);
            query.setParameter("bankAccountID", bankAccountID);
//            transaction.commit();
            List result = query.list();
            for (Object client : result) {
                return (Client) client;
            }
        } catch (HibernateException e) {
//            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }
        return null;
    }

    @Override
    public Client getClientByLogin(String login) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            String hql = "select C from by.academy.it.entity.Client C where C.login=:login";
            Query query = session.createQuery(hql);
            query.setParameter("login", login);
//            transaction.commit();
            List result = query.list();
            for (Object client : result) {
                return (Client) client;
            }
        } catch (HibernateException e) {
//            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }
        return null;
    }

    /*   @Override
        public boolean payOrder(int orderID) {
            return false;
        }
    */
    @Override
    public boolean loginUser(String login, String password) throws DaoException {
//        List list = Collections.emptyList();
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Client.class);
            criteria.add(Restrictions.eq("login", login));


            List list = criteria.list();
//            Client client = (Client)list.get(0);
            String resievedPass = ((Client) list.get(0)).getPassword();
//            transaction.commit();
            return password.equals(resievedPass);
//            log.info("getListCommentByNewsId, size=" + list.size());
        } catch (HibernateException e) {
//            transaction.rollback();
//            log.error("Error get " + News.class + " in Dao" + e);
            throw new DaoException(e);
        }



/*
//        logger.info("->JDBCClientDAOImpl.boolean loginUser(login = " + login + ", password = " + password + ")");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = new Client();

        try {
            connection = dataSource.getConection();
            preparedStatement = connection.prepareStatement(SQL_GET_CLIENT_ITEM);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            logger.info("--JDBCClientDAOImpl.loginUser executeQuery()");

            if (resultSet.next()) {
                logger.info("<-JDBCClientDAOImpl.boolean loginUser(login = " + login + ", password = " + password + ") = " + resultSet.getString(2).equals(password));
                return (resultSet.getString(2).equals(password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("--JDBCClientDAOImpl.create.Exception = " + e.getErrorCode());
        } finally {
            DBUtils.close(preparedStatement, connection);
        }
        logger.info("<-JDBCClientDAOImpl.boolean loginUser(login = " + login + ", password = " + password + ") = false");
        return false;*/
    }

    @Override
    public boolean logoutUser() {
        return false;
    }
}
