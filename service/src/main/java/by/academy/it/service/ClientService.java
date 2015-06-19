package by.academy.it.service;

import by.academy.it.dao.ClientDao;
import by.academy.it.dao.IClientDao;
//import by.academy.it.dao.impl.JDBCClientDAOImpl;
import by.academy.it.dao.exceptions.DaoException;
import by.academy.it.entity.Client;
import by.academy.it.entity.UserRole;

import java.util.List;

public class ClientService {

    private IClientDao clientDAO = new ClientDao();

    public int create(String login, String password, String firstName, String lastName, int bankAccountID) throws DaoException {
        return clientDAO.create(new Client(login, password, firstName, lastName, UserRole.CLIENT, bankAccountID));
    }

    public Client getClientByLogin(String login) throws DaoException {
        return clientDAO.getClientByLogin(login);
    }

    public Client getClientByBankAccountID(int bankAccountID) throws DaoException {
        return clientDAO.getClientByBankAccountID(bankAccountID);
    }

    public boolean login(String login, String password) throws DaoException {
        return clientDAO.loginUser(login, password);
    }


  /*  public void deleteClient(String login){
        clientDAO.delete(login);
    }*/

    public List<Client> getAll() throws DaoException {
        return clientDAO.getAll();
    }

}
