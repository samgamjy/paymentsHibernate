package by.academy.it.service;

import by.academy.it.dao.BankAccountDao;
import by.academy.it.dao.IBankAccountDao;
import by.academy.it.dao.IOrderDao;
import by.academy.it.dao.OrderDao;
//import by.academy.it.dao.impl.JDBCOrderDAOImpl;
import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.entity.BankAccount;
import by.academy.it.entity.Order;

import java.util.List;

/**
 * Created by Sam on 15.05.2015.
 */
public class OrderService {
    private IOrderDao orderDAO = new OrderDao();
    private IBankAccountDao bankAccountDao = new BankAccountDao();

    public int createOrder(int bankAccountID, double sum) {
        try {
            BankAccount bankAccount = bankAccountDao.get(bankAccountID);

            Order order = new Order(sum, false);
            order.setBankAccount(bankAccount);
            orderDAO.saveOrUpdate(order);

            bankAccount.getOrderList().add(order);
            bankAccountDao.saveOrUpdate(bankAccount);
            return order.getId();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public void deleteOrder(int orderID) {
//        orderDAO.deleteOrder(orderID);
//    }

    public Order getOrder(int orderID) throws DaoException {
        return orderDAO.get(orderID);
    }

    public List<Order> getOrderList() throws DaoException {
        return orderDAO.getAll();
    }

    public List<Order> getOrderListByBankAccount(int bankAccountID) throws DaoException {
        return orderDAO.getOrderListByBankAccount(bankAccountID);
    }

    public void setOrderPay(int orderID, boolean isPaid) throws DaoException {
        orderDAO.setOrderPay(orderID, isPaid);
    }

}
