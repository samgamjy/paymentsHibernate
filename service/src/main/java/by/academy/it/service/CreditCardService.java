package by.academy.it.service;

import by.academy.it.dao.CreditCardDao;
import by.academy.it.dao.ICreditCardDao;
//import by.academy.it.dao.impl.JDBCCreditCardDAOImpl;
import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.entity.CreditCard;

/**
 * Created by Sam on 06.05.2015.
 */
public class CreditCardService {

    private ICreditCardDao creditCardDao = new CreditCardDao();

    public int create() throws DaoException {
        return creditCardDao.create(new CreditCard());
    }

    public void delete(int creditCardID) throws DaoException {
        CreditCard creditCard = creditCardDao.get(creditCardID);
        if (creditCard != null) {
            creditCardDao.delete(creditCard);
        }
    }
}
