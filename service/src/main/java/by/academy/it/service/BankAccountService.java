package by.academy.it.service;

import by.academy.it.dao.BankAccountDao;
import by.academy.it.dao.IBankAccountDao;
import by.academy.it.dao.exceptions.DaoException;
//import by.academy.it.dao.impl.JDBCBankAccountDAOImpl;
import by.academy.it.entity.BankAccount;
import by.academy.it.entity.Order;

import java.util.List;

/**
 * Created by Sam on 06.05.2015.
 */
public class BankAccountService {
    private IBankAccountDao bankAccountDAO = new BankAccountDao();

    public int create(boolean blocked, double sum, int creditCardID) throws DaoException {
        return bankAccountDAO.create(new BankAccount(sum, blocked, creditCardID));
    }

    public BankAccount getBankAccount(int bankAccountID) throws DaoException {
        return bankAccountDAO.get(bankAccountID);
    }

/*    public void deleteBankAccount(int bankAccountID){
       bankAccountDAO.delete(bankAccountID);
    }*/

    public void blockBankAccount(int bankAccountID, boolean isBlock) throws DaoException {
        bankAccountDAO.blockCreditCard(bankAccountID, isBlock);
    }

    public void transferMoney(int dstBankAccountID, int srcBankAccountID, double transferSum) throws DaoException {
        bankAccountDAO.transferMoney(dstBankAccountID, srcBankAccountID, transferSum);
    }

    public void payOrder(Order order) throws DaoException {
        bankAccountDAO.payOrder(order);
    }

    public List<Integer> getBankAccountIDList() throws DaoException {
        return bankAccountDAO.getBankAccountIDList();
    }

    public List<BankAccount> getAll() throws DaoException {
        return bankAccountDAO.getAll();
    }
}
