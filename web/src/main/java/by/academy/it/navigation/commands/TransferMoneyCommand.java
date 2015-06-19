package by.academy.it.navigation.commands;

import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.entity.Client;
import by.academy.it.service.BankAccountService;
import by.academy.it.service.ClientService;
import by.academy.it.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.academy.it.resources.constant.Constants.*;


public class TransferMoneyCommand implements Command {

    private Transaction transaction = null;

    @Override
    public String execCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Session utilSession = HibernateUtil.getHibernateUtil().getSession();
            transaction = utilSession.beginTransaction();
            int srcBankAccountID = Integer.parseInt(request.getParameter(PARAM_BANK_ACCOUNT_ID));
            int dstBankAccountID = Integer.parseInt(request.getParameter(PARAM_BANK_ACCOUNT_TRANSFER_ID));
            double sumForTransfer = Double.parseDouble(request.getParameter(PARAM_BANK_ACCOUNT_TRANSFER_SUM));

            BankAccountService bankAccountService = new BankAccountService();
            bankAccountService.transferMoney(dstBankAccountID, srcBankAccountID, sumForTransfer);
            ClientService clientService = new ClientService();
            Client client = clientService.getClientByBankAccountID(srcBankAccountID);

            request.setAttribute(PARAM_CLIENT_ITEM, client);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(PAGE_CLIENT_INFO);
        requestDispatcher.forward(request, response);

        return PAGE_CLIENT_INFO;
    }
}
