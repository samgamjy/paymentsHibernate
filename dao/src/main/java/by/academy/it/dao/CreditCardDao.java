package by.academy.it.dao;

import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.entity.CreditCard;
import by.academy.it.entity.User;
import by.academy.it.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by sam on 18.06.2015.
 */
public class CreditCardDao extends BaseDao<CreditCard> implements ICreditCardDao {
//    private Transaction transaction = null;
    @Override
    public int create(CreditCard creditCard) throws DaoException {
        try {
            saveOrUpdate(creditCard);

        }catch (DaoException e){
            e.printStackTrace();
            throw e;
        }
        return creditCard.getId();
    }

/*
    @Override
    public void delete(int creditCardID) {
    }
*/

    @Override
    public CreditCard getCreditCardByBankAccountID(int bankAccountID) throws DaoException {
        try {
            Session session = HibernateUtil.getHibernateUtil().getSession();
//            transaction = session.beginTransaction();
            String hql = "select C from by.academy.it.entity.CreditCard C,by.academy.it.entity.BankAccount B where B.id=:bankAccountID and B.creditCardID=:C.id";
            Query query = session.createQuery(hql);
            query.setParameter("bankAccountID", bankAccountID);
//            transaction.commit();
            List result = query.list();
            for (Object creditCard : result) {
                return (CreditCard) creditCard;
            }
        } catch (HibernateException e) {
//            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }
        return null;
    }
}
