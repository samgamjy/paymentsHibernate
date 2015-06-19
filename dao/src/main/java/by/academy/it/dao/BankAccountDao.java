package by.academy.it.dao;

import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.entity.BankAccount;
import by.academy.it.entity.Order;
import by.academy.it.utils.DBUtils;
import by.academy.it.utils.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by sam on 18.06.2015.
 */
public class BankAccountDao extends BaseDao<BankAccount> implements IBankAccountDao{

//    private Transaction transaction = null;

    @Override
    public int create(BankAccount bankAccount) throws DaoException {
        try {
            saveOrUpdate(bankAccount);

        }catch (DaoException e){
            e.printStackTrace();
            throw e;
        }
        return bankAccount.getId();
   }

    @Override
    public void blockCreditCard(int bankAccountID, boolean isBlock) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
            BankAccount bankAccount = (BankAccount) session.get(BankAccount.class, bankAccountID);
//            System.out.println(book);
            bankAccount.setBlocked(isBlock);
//            System.out.println(book);
            session.flush();
//            System.out.println(book);
        } catch (HibernateException e) {
//            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }

    }


    private void transferMoney(int bankAccountID, double transferSum) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
            BankAccount bankAccount = (BankAccount) session.get(BankAccount.class, bankAccountID);
//            System.out.println(book);
            double sum = bankAccount.getSum();
            bankAccount.setSum(sum + transferSum);
//            System.out.println(book);
            session.flush();
//            System.out.println(book);
        } catch (HibernateException e) {
//            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }
    }

    @Override
    public void transferMoney(int dstBankAccountID, int srcBankAccountID, double transferSum) throws DaoException {
//        try{
//            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            transferMoney(srcBankAccountID, -transferSum);
            transferMoney(dstBankAccountID, transferSum);


 /*       } catch (HibernateException e){
            transaction.rollback();
//            log.error("Error get " + News.class + " in Dao" + e);
            throw new DaoException(e);

        }
*/
    }

    @Override
    public List<Integer> getBankAccountIDList() throws DaoException {
        List list = Collections.emptyList();
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
            String hql = "select B.id from by.academy.it.entity.BankAccount B";
            Query query = session.createQuery(hql);
//            transaction.commit();
             list = query.list();



//            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
//            Criteria criteria = session.createCriteria(BankAccount.class);
//            criteria.add(Restrictions.eq("bankAccount", bankAccountID));
//            list = criteria.list().;
//            transaction.commit();
//            log.info("getListCommentByNewsId, size=" + list.size());
        } catch (HibernateException e) {
//            transaction.rollback();
//            log.error("Error get " + News.class + " in Dao" + e);
            throw new DaoException(e);
        }
        return list;
    }

    @Override
    public void payOrder(Order order) throws DaoException {
//        logger.info("->JDBCOrderDAOImpl.boolean payOrder(order = " + order + ")");

//        List list = Collections.emptyList();
        try {
//            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            transferMoney(order.getBankAccount().getId(), -order.getSum());

//            transaction.commit();
//            log.info("getListCommentByNewsId, size=" + list.size());
        } catch (HibernateException e) {
//            transaction.rollback();
//            log.error("Error get " + News.class + " in Dao" + e);
            throw new DaoException(e);
        }
//        return list;
    }
}
