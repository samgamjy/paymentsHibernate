package by.academy.it.dao;

import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.entity.Order;
import by.academy.it.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Collections;
import java.util.List;

/**
 * Created by sam on 18.06.2015.
 */
public class OrderDao extends BaseDao<Order> implements IOrderDao {

//    private Transaction transaction = null;

    @Override
    public List<Order> getOrderListByBankAccount(int bankAccountID) throws DaoException {
        List list = Collections.emptyList();
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Order.class);
            criteria.add(Restrictions.eq("bankAccount.id", bankAccountID));
            list = criteria.list();
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
    public void setOrderPay(int orderID, boolean isPaid) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
            Order order = (Order) session.get(Order.class, orderID);
//            System.out.println(book);
            order.setPaid(isPaid);
            boolean isDirty = session.isDirty();
//            System.out.println(book);
            session.flush();
            isDirty = session.isDirty();
//            System.out.println(book);
        } catch (HibernateException e) {
//            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }

    }
}
